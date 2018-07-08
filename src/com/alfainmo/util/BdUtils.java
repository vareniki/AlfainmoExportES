package com.alfainmo.util;

import java.sql.*;
import java.util.*;

import com.alfainmo.beans.BeanObject;
import com.alfainmo.beans.TbColumn;
import com.alfainmo.extra.AlfaException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class BdUtils {

    private static final int SQL_DELETE = 1;
    private static final int SQL_INSERT = 2;
    private static final int SQL_UPDATE = 3;
    private static final int SQL_REPLACE = 4;
    private static Map<String, Map<String, TbColumn>> metaData = null;
    private String bdURL = null;
    private String bdUser = null;
    private String bdPass = null;
    private Connection connection = null;
    private int nconex = 0;
    private boolean proteccDesbord = true;
    private int lastIndex = 0;

    /**
     * @param bdDriver
     * @param bdURL
     * @param bdUser
     * @param bdPass
     * @throws AlfaException
     */
    public BdUtils(String bdDriver, String bdURL, String bdUser, String bdPass) throws AlfaException {

        try {
            Class.forName(bdDriver);
        } catch (ClassNotFoundException ex) {
            throw new AlfaException(ex);
        }
        this.bdURL = bdURL;
        this.bdUser = bdUser;
        this.bdPass = bdPass;
    }

    /**
     * @param rs
     * @throws AlfaException
     */
    public static void beforFirstRS(ResultSet rs) throws AlfaException {
        try {
            if (rs != null) {
                rs.beforeFirst();
            }
        } catch (SQLException ex) {
            throw new AlfaException(ex);
        }
    }

    /**
     * @param <T>
     * @param sqlSelect
     * @param beanWhere
     * @return
     * @throws AlfaException
     */
    private static <T extends BeanObject> String addFiltroSQL(String sqlSelect, T beanWhere)
            throws AlfaException {

        StringBuilder sqlWhere = new StringBuilder("");
        if (beanWhere != null) {
            Map<String, Object> camposWhere = (new BeanClass(beanWhere.getClass(), true)).getPropertiesMap(beanWhere);
            for (String campo : camposWhere.keySet()) {
                sqlWhere.append(" AND ").append(campo).append("='").append(camposWhere.get(campo)).append("'");
            }
            if (sqlWhere.length() != 0) {
                sqlWhere.delete(0, 5);
            }
        }
        if (sqlWhere.length() > 0) {
            sqlSelect += " WHERE " + sqlWhere.toString();
        }
        return sqlSelect;
    }

    /**
     * @param tbName
     * @return
     * @throws SQLException
     */
    private Map<String, TbColumn> cargarCamposEsquema(String tbName) throws SQLException {
        Map<String, TbColumn> tbCols = new HashMap<>();

        ResultSet rs = connection.getMetaData().getColumns(null, "%", tbName, "%");
        while (rs.next()) {
            TbColumn tbCol = new TbColumn();
            tbCol.setColumnName(rs.getString("COLUMN_NAME"));
            tbCol.setTypeName(rs.getString("TYPE_NAME"));
            tbCol.setColumnSize(rs.getInt("COLUMN_SIZE"));
            tbCol.setDecimalDigits(rs.getInt("DECIMAL_DIGITS"));
            tbCol.setNullable(rs.getInt("NULLABLE") == 1);
            tbCols.put(tbCol.getColumnName(), tbCol);
        }
        return tbCols;
    }

    /**
     * @throws SQLException
     */
    private void cargarEsquema() throws SQLException {
        if (connection == null) {
            return;
        }
        metaData = new HashMap<>();

        String[] names = {"TABLE"};
        ResultSet tableNames = connection.getMetaData().getTables(null, "%", "%", names);
        while (tableNames.next()) {
            String tbName = tableNames.getString("TABLE_NAME");
            metaData.put(tbName, cargarCamposEsquema(tbName));
        }
    }

    /**
     * @param sql
     * @param tabla
     * @param campos
     * @return
     * @throws AlfaException
     */
    private int ejecutarUpdate(String sql, String tabla, Map<String, Object>... campos) throws AlfaException {

        int result = 0;
        lastIndex = 0;

        connect();
        try {
            try (PreparedStatement ps = prepararStatement(sql, tabla, campos)) {
                result = ps.executeUpdate();

                if (result > 0) {
                    ResultSet rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        lastIndex = rs.getInt(1);
                    }
                }

            }
        } catch (SQLException ex) {
            throw new AlfaException(ex);
        } finally {
            disconnect();
        }
        return result;
    }

    /**
     * @param camposTb
     * @param ps
     * @param i
     * @param campos
     * @return
     * @throws SQLException
     */
    private int preparararCamposPS(
            Map<String, TbColumn> camposTb, PreparedStatement ps, int i, Map<String, Object> campos) throws SQLException {

        if (campos == null) {
            return i;
        }

        for (String campo : campos.keySet()) {
            Object valor = campos.get(campo);
            if (valor instanceof String) {
                if (proteccDesbord && camposTb != null) {
                    final TbColumn tbCol = camposTb.get(campo);
                    if (tbCol != null && ((String) valor).length() > tbCol.getColumnSize()) {
                        valor = ((String) valor).substring(0, tbCol.getColumnSize());
                    }
                }
                ps.setString(i, (String) valor);
            } else if (valor instanceof Integer) {
                ps.setInt(i, (Integer) valor);
            } else if (valor instanceof Long) {
                ps.setLong(i, (Long) valor);
            } else if (valor instanceof Float) {
                ps.setFloat(i, (Float) valor);
            } else if (valor instanceof Double) {
                ps.setDouble(i, (Double) valor);
            } else if (valor instanceof java.util.Date) {
                ps.setDate(i, new java.sql.Date(((java.util.Date) valor).getTime()));
            } else if (valor instanceof java.sql.Time) {
                ps.setTime(i, (java.sql.Time) valor);
            } else if (valor instanceof Timestamp) {
                ps.setTimestamp(i, (java.sql.Timestamp) valor);
            } else if (valor instanceof Boolean) {
                ps.setBoolean(i, (java.lang.Boolean) valor);
            }
            i++;
        }
        return i;
    }

    /**
     * @param sql
     * @param tabla
     * @param listaDeCampos
     * @return
     * @throws AlfaException
     */
    private PreparedStatement prepararStatement(String sql, final String tabla, Map<String, Object>... listaDeCampos)
            throws AlfaException {

        Map<String, TbColumn> camposTb = null;
        if (tabla != null && proteccDesbord) {
            camposTb = metaData.get(tabla);
        }

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int i = 1;
            for (Map<String, Object> campos : listaDeCampos) {
                i = preparararCamposPS(camposTb, ps, i, campos);
            }
        } catch (SQLException ex) {
            throw new AlfaException(ex);
        }
        return ps;
    }

    /**
     *
     * @param tabla
     * @param bean
     * @return
     * @throws AlfaException
     */
    public int addData(String tabla, BeanObject bean) throws AlfaException {
        return ejecutarUpdate(tabla, bean, SQL_INSERT, null);
    }

    /**
     * @param tabla     String
     * @param bean      BeanObject
     * @param beanWhere BeanObject
     * @return int
     * @throws AlfaException
     */
    public int addData(String tabla, BeanObject bean, BeanObject beanWhere) throws AlfaException {
        int result = 0;
        if (!checkContent(tabla, beanWhere)) {
            result = addData(tabla, bean);
        }
        return result;
    }

    /**
     * @param tabla String
     * @param datos Collection
     * @return int
     * @throws AlfaException
     */
    public int addData(String tabla, Collection<? extends BeanObject> datos) throws AlfaException {
        int result = 0;
        for (BeanObject obj : datos) {
            result += addData(tabla, obj);
        }
        return result;
    }

    /**
     * @param tabla
     * @param bean
     * @return
     * @throws AlfaException
     */
    public int replaceData(String tabla, BeanObject bean) throws AlfaException {
        return ejecutarUpdate(tabla, bean, SQL_REPLACE, null);
    }

    /**
     * @param tabla
     * @param bean
     * @return
     * @throws AlfaException
     */
    public int addDataAndCommit(String tabla, BeanObject bean) throws AlfaException {
        int result = 0;
        connect();
        try {
            result = ejecutarUpdate(tabla, bean, SQL_INSERT, null);
            commit();
        } catch (AlfaException e) {
            rollback();
            throw new AlfaException(e);
        } finally {
            disconnect();
        }
        return result;
    }

    /**
     * @param tabla
     * @param bean
     * @param beanWhere
     * @return
     * @throws AlfaException
     */
    public int addDataAndCommit(String tabla, BeanObject bean, BeanObject beanWhere) throws AlfaException {
        int result = 0;
        connect();
        try {
            result = addData(tabla, bean, beanWhere);
            commit();
        } catch (AlfaException e) {
            rollback();
            throw new AlfaException(e);
        } finally {
            disconnect();
        }
        return result;
    }

    /**
     * @param tabla
     * @param datos
     * @return
     * @throws AlfaException
     */
    public int addDataAndCommit(String tabla, Collection<? extends BeanObject> datos) throws AlfaException {
        int result = 0;
        connect();
        try {
            result = addData(tabla, datos);
            commit();
        } catch (AlfaException e) {
            rollback();
            throw new AlfaException(e);
        } finally {
            disconnect();
        }
        return result;
    }

    /**
     * @param tabla String
     * @param bean  BeanObject
     * @return int
     * @throws AlfaException
     */
    @SuppressWarnings("unchecked")
    public boolean checkContent(String tabla, BeanObject bean) throws AlfaException {

        if (tabla == null || tabla.equals("") || bean == null) {
            throw new AlfaException("Par�metros erroneos en checkContent.");
        }

        Map<String, Object> camposWhere = (new BeanClass(bean.getClass(), true)).getPropertiesMap(bean);

        StringBuilder sqlWhere = new StringBuilder("");
        for (String campo : camposWhere.keySet()) {
            sqlWhere.append(" AND ").append(campo).append("=?");
        }
        if (sqlWhere.length() != 0) {
            sqlWhere.delete(0, 5);
        } else {
            throw new AlfaException("Sin parámetros de búsqueda SQL en checkContent.");
        }

        boolean result = false;
        try {
            try (PreparedStatement ps = prepararStatement(
                    "SELECT 0 FROM " + tabla + " WHERE " + sqlWhere.toString(), null, camposWhere)) {
                result = ps.executeQuery().next();
            }
        } catch (SQLException ex) {
            throw new AlfaException(ex);
        }
        return result;
    }

    /**
     * @throws AlfaException
     */
    public void commit() throws AlfaException {
        if (connection == null || nconex == 0) {
            return;
        }
        try {
            connection.commit();
        } catch (SQLException ex) {
            throw new AlfaException(ex);
        }
    }

    /**
     * @return Connection
     * @throws AlfaException
     */
    public Connection connect() throws AlfaException {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(bdURL, bdUser, bdPass);
                connection.setAutoCommit(false);

                if (metaData == null) {
                    cargarEsquema();
                }
            } catch (SQLException ex) {
                throw new AlfaException(ex);
            }
        }
        nconex++;
        return connection;
    }

    /**
     * @param tabla String
     * @param bean  BeanObject
     * @return int
     * @throws AlfaException
     */
    public int delData(String tabla, BeanObject bean) throws AlfaException {
        return ejecutarUpdate(tabla, bean, SQL_DELETE, null);
    }

    /**
     * @throws AlfaException
     */
    public void disconnect() throws AlfaException {
        if (connection == null || nconex == 0) {
            return;
        }
        nconex--;
        if (nconex == 0) {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new AlfaException(ex);
            }
            connection = null;
        }
    }

    /**
     * @param sql String
     * @return ResultSet
     * @throws AlfaException
     */
    public ResultSet executeQuery(String sql) throws AlfaException {
        return executeQuery(sql, true);
    }

    /**
     * @param sql      String
     * @param readOnly boolean
     * @return ResultSet
     * @throws AlfaException
     */
    public ResultSet executeQuery(String sql, boolean readOnly) throws AlfaException {

        ResultSet result = null;
        connect();
        try {
            int concur = (readOnly) ? ResultSet.CONCUR_READ_ONLY : ResultSet.CONCUR_UPDATABLE;
            Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, concur);
            stmt.setFetchSize(25);
            result = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            throw new AlfaException(ex);
        } finally {
            disconnect();
        }
        return result;
    }

    /**
     * @param sql String
     * @return int
     * @throws AlfaException
     */
    public int executeUpdate(String sql) throws AlfaException {
        int result = 0;
        connect();
        try {
            result = connection.createStatement().executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException ex) {
            throw new AlfaException(ex);
        } finally {
            disconnect();
        }
        return result;
    }

    /**
     * @param tabla
     * @param bean
     * @param oper
     * @param beanWhere
     * @return
     * @throws AlfaException
     */
    public int ejecutarUpdate(String tabla, BeanObject bean, int oper, BeanObject beanWhere) throws AlfaException {
        return ejecutarUpdate(tabla, bean, oper, beanWhere, false);
    }

    /**
     * @param tabla
     * @param bean
     * @param oper
     * @param beanWhere
     * @param setNulls
     * @return
     * @throws AlfaException
     */
    @SuppressWarnings("unchecked")
    public int ejecutarUpdate(String tabla, BeanObject bean, int oper, BeanObject beanWhere,
                              final boolean setNulls) throws AlfaException {

        if (oper != SQL_INSERT && oper != SQL_DELETE && oper != SQL_UPDATE && oper != SQL_REPLACE) {
            throw new AlfaException("Funcion implementada para DELETES, INSERTS y UPDATES y REPLACES.");
        }

        BeanClass beanClass = new BeanClass(bean.getClass(), oper == SQL_INSERT || oper == SQL_DELETE || oper == SQL_REPLACE);
        List<String> camposNulos = new ArrayList<>();
        Map<String, Object> campos = beanClass.getPropertiesMap(bean, camposNulos);

        if (campos.isEmpty()) {
            return 0;
        }

        Map<String, Object> camposWhere = null;
        if (beanWhere != null) {
            beanClass = new BeanClass(beanWhere.getClass());
            camposWhere = beanClass.getPropertiesMap(beanWhere);
        }

        StringBuilder camposSQL = new StringBuilder("");
        StringBuilder valoresSQL = new StringBuilder("");
        String sql = null;
        switch (oper) {
            case SQL_INSERT:
            case SQL_REPLACE:
                for (String campo : campos.keySet()) {
                    camposSQL.append(",").append(campo);
                    valoresSQL.append(",?");
                }
                if (camposSQL.length() != 0) {
                    camposSQL.delete(0, 1);
                    valoresSQL.delete(0, 1);
                }
                if (oper == SQL_INSERT) {
                    sql = String.format("INSERT INTO %s(%s) VALUES (%s)", tabla, camposSQL.toString(), valoresSQL.toString());
                } else {
                    sql = String.format("REPLACE INTO %s(%s) VALUES (%s)", tabla, camposSQL.toString(), valoresSQL.toString());
                }
                break;
            case SQL_DELETE:
                for (String campo : campos.keySet()) {
                    camposSQL.append(" AND ").append(campo).append("=?");
                }
                if (camposSQL.length() != 0) {
                    camposSQL.delete(0, 5);
                }
                sql = String.format("DELETE FROM %s WHERE %s", tabla, camposSQL.toString());
                break;
            case SQL_UPDATE:
                // Campos no nulos
                for (String campo : campos.keySet()) {
                    camposSQL.append(",").append(campo).append("=?");
                }
                // Campos nulos
                if (setNulls) {
                    for (String campo : camposNulos) {
                        camposSQL.append(",").append(campo).append("=NULL");
                    }
                }
                // Elimina la primera come en caso de que existan campos en SET
                if (camposSQL.length() != 0) {
                    camposSQL.delete(0, 1);
                }
                // Campos de filtro (where)
                StringBuilder camposSQLWhere = new StringBuilder("");
                if (camposWhere != null) {
                    for (String campo : camposWhere.keySet()) {
                        camposSQLWhere.append(" AND ").append(campo).append("=?");
                    }

                    if (camposSQLWhere.length() != 0) {
                        camposSQLWhere.delete(0, 5);
                    }
                }
                sql = String.format("UPDATE %s SET %s WHERE %s", tabla, camposSQL.toString(), camposSQLWhere.toString());

                break;

            default:
                break;
        }
        if (camposSQL.length() == 0) {
            throw new AlfaException("Consulta no parametrizada.");
        }

        return ejecutarUpdate(sql, tabla, campos, camposWhere);
    }

    /**
     * @param sql
     * @param clase
     * @param <T>
     * @return
     * @throws AlfaException
     */
    public <T> T getData(String sql, Class<T> clase) throws AlfaException {
        T result = null;

        connect();
        try {
            QueryRunner queryRunner = new QueryRunner();
            try {
                result = (T) queryRunner.query(connection, sql, new BeanHandler(clase));
            } catch (SQLException ex) {
                throw new AlfaException(ex);
            }
        } finally {
            disconnect();
        }
        return result;
    }

    /**
     * @param <T>
     * @param tabla
     * @param beanWhere
     * @return
     * @throws AlfaException
     */
    public <T extends BeanObject> T getData(String tabla, T beanWhere) throws AlfaException {
        return (T) getData(addFiltroSQL("SELECT * FROM " + tabla, beanWhere), beanWhere.getClass());
    }

    /**
     * @param <T>
     * @param sql
     * @param clase
     * @return
     * @throws AlfaException
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getDataList(String sql, Class<T> clase) throws AlfaException {
        List<T> result = null;

        connect();
        try {
            QueryRunner queryRunner = new QueryRunner();
            try {
                result = (List<T>) queryRunner.query(connection, sql, new BeanListHandler(clase));
            } catch (SQLException ex) {
                throw new AlfaException(ex);
            }
        } finally {
            disconnect();
        }
        return result;
    }

    /**
     * @param <T>
     * @param tabla
     * @param beanWhere
     * @return
     * @throws AlfaException
     */
    @SuppressWarnings("unchecked")
    public <T extends BeanObject> List<T> getDataList(String tabla, T beanWhere) throws AlfaException {
        return (List<T>) getDataList(addFiltroSQL("SELECT * FROM " + tabla, beanWhere), beanWhere.getClass());
    }

    /**
     * @param sql
     * @return
     * @throws AlfaException
     */
    public String getStringValue(String sql) throws AlfaException {
        String result = null;
        connect();
        try {
            try (ResultSet rs = executeQuery(sql)) {
                if (rs.next()) {
                    result = rs.getString(1);
                }
            }
        } catch (SQLException ex) {
            throw new AlfaException(ex);
        } finally {
            disconnect();
        }
        return result;
    }

    public boolean isProteccionDesbordamiento() {
        return proteccDesbord;
    }

    public void setProteccionDesbordamiento(boolean proteccDesbord) {
        this.proteccDesbord = proteccDesbord;
    }

    /**
     * @throws AlfaException
     */
    public void rollback() throws AlfaException {
        if (connection == null || nconex == 0) {
            return;
        }
        try {
            if (!connection.getAutoCommit()) {
                connection.rollback();
            }
        } catch (SQLException ex) {
            throw new AlfaException(ex);
        }
    }

    /**
     * @param tabla     String
     * @param bean      BeanObject
     * @param beanWhere BeanObject
     * @return int
     * @throws AlfaException
     */
    public int setData(String tabla, BeanObject bean, BeanObject beanWhere) throws AlfaException {
        return ejecutarUpdate(tabla, bean, SQL_UPDATE, beanWhere, false);
    }

    /**
     * @param tabla
     * @param bean
     * @param beanWhere
     * @param setNulls
     * @return
     * @throws AlfaException
     */
    public int setData(String tabla, BeanObject bean, BeanObject beanWhere, boolean setNulls) throws AlfaException {
        return ejecutarUpdate(tabla, bean, SQL_UPDATE, beanWhere, setNulls);
    }

    /**
     * @param tabla
     * @param bean
     * @param beanWhere
     * @return
     * @throws AlfaException
     */
    public int setDataAndCommit(String tabla, BeanObject bean, BeanObject beanWhere) throws AlfaException {
        int result = 0;
        connect();
        try {
            result = ejecutarUpdate(tabla, bean, SQL_UPDATE, beanWhere, false);
            commit();
        } catch (AlfaException e) {
            rollback();
            throw new AlfaException(e);
        } finally {
            disconnect();
        }
        return result;
    }

    /**
     * @param tabla
     * @param bean
     * @param beanWhere
     * @param setNulls
     * @return
     * @throws AlfaException
     */
    public int setDataAndCommit(String tabla, BeanObject bean, BeanObject beanWhere, boolean setNulls) throws AlfaException {
        int result = 0;
        connect();
        try {
            result = ejecutarUpdate(tabla, bean, SQL_UPDATE, beanWhere, setNulls);
            commit();
        } catch (AlfaException e) {
            rollback();
            throw new AlfaException(e);
        } finally {
            disconnect();
        }
        return result;
    }

    /**
     * @return
     */
    public int getLastIndex() {
        return lastIndex;
    }
}
