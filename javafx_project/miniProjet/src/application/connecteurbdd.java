package application;
import java.sql.*;
 
public class connecteurbdd {
	public static Connection getConnection() {
		Connection connection = null;
		String url = "jdbc:mysql://localhost:3306/project";
		String username = "root", password = "2513";
		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("Connexion à la base de données réussie !");
            
            // Exemple : Exécuter une requête pour récupérer des données
            try {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM students");
                while (resultSet.next()) {
                    String cne = resultSet.getString("cne");
                    String nom = resultSet.getString("lastName");
                    String prenom = resultSet.getString("firstName");
                    String dateN = resultSet.getString("birthDate");
                    String studentField = resultSet.getString("studentField");
                    String currentLevel = resultSet.getString("currentLevel");
                    String email = resultSet.getString("email");

                    System.out.println("CNE: " + cne + ", date de naiss: " + dateN+", Nom: " + nom + ", Prénom: " + prenom + ", student field: " + studentField+ ", current level: " + currentLevel+ ", email: " + email);
                }
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            // Fermer la connexion
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Échec de la connexion à la base de données !");
        }
    }
}
