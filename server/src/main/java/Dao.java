import java.sql.*;

public class Dao {
    private static final String URL = "jdbc:postgresql://hgrid2:5432/votaciones";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    private static final String QUERY = """
                    SELECT
                        mv.id as mesa_id,
                        pv.id as puesto_id,
                        m.nombre as municipio,
                        d.nombre as departamento
                    FROM ciudadano c
                    JOIN mesa_votacion mv ON c.mesa_id = mv.id
                    JOIN puesto_votacion pv ON mv.puesto_id = pv.id
                    JOIN municipio m ON pv.municipio_id = m.id
                    JOIN departamento d ON m.departamento_id = d.id
                    WHERE c.documento = ?
            """;

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public String consultarMesaVotacion(String documento) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(QUERY)) {
            
            stmt.setString(1, documento);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return String.format("MESA: %d, PUESTO: %d, %s, %s",
                        rs.getInt("mesa_id"),
                        rs.getInt("puesto_id"),
                        rs.getString("municipio"),
                        rs.getString("departamento")
                    );
                }
            
            }
        }
    }
}