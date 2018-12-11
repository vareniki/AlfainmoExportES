package com.alfainmo.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import com.alfainmo.extra.AlfaException;

public class BeanClass {

    private boolean ceroIsNull;
    private final Collection<Method> methods = new ArrayList<>();
    private Class<?> originalClass = null;

    public BeanClass(Class<?> classBean) {
        this(classBean, true);
    }

    /**
     * @param classBean
     * @param ceroIsNull
     */
    public BeanClass(Class<?> classBean, boolean ceroIsNull) {

        this.ceroIsNull = ceroIsNull;
        this.originalClass = classBean;

        Method[] classMethods = classBean.getMethods();
        for (Method metodoGet : classMethods) {
            if (metodoGet.getDeclaringClass() != Object.class && metodoGet.getName().startsWith("get")) {
                methods.add(metodoGet);
            }
        }
    }

    public Class<?> getOriginalClass() {
        return originalClass;
    }

    /**
     * @param bean Object
     * @return Map
     * @throws AlfaException
     */
    public Map<String, Object> getPropertiesMap(Object bean) throws AlfaException {
        return getPropertiesMap(bean, null);
    }

    /**
     * @param bean
     * @param nullProperties
     * @return
     * @throws AlfaException
     */
    public Map<String, Object> getPropertiesMap(Object bean, List<String> nullProperties) throws AlfaException {
        Map<String, Object> result = new HashMap<>();

        for (Method metodoGet : methods) {
            Object obj = null;
            try {
                obj = metodoGet.invoke(bean);
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                throw new AlfaException(e);
            }
            // Si el objeto es nulo entonces no se aÒade al "map"
            final String campo = metodoGet.getName().substring(3).toLowerCase();
            if (obj != null) {
                boolean l_ceroIsNull = this.ceroIsNull;

                if (obj.getClass().isPrimitive()) {
                    String stro = (String) obj;
                    if (!stro.equals("0") || !l_ceroIsNull) {
                        if (stro.indexOf('.') > -1 || stro.indexOf(',') > -1) {
                            result.put(campo, Float.valueOf(stro));
                        } else {
                            result.put(campo, Integer.valueOf(stro));
                        }
                    } else if (nullProperties != null) {
                        nullProperties.add(campo);
                    }

                } else if (obj instanceof Number) {

                    if (!l_ceroIsNull || (((Number) obj).floatValue() != 0)) {
                        result.put(campo, obj);
                    } else if (nullProperties != null) {
                        nullProperties.add(campo);
                    }

                } else {
                    result.put(campo, obj);
                }

            } else if (nullProperties != null) {
                nullProperties.add(campo);
            }
        }

        return result;
    }
}
