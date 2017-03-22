package org.springframework.samples.petclinic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Set;

import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetType;

public class JDBCApplication {

	public static void main(String[] args) {
		System.out.println("-------- Test de conexión con MySQL ------------");

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("No encuentro el driver en el Classpath");
			e.printStackTrace();
			return;
		}

		System.out.println("Driver instalado y funcionando");
		Connection connection = null;
		Statement statement = null;
		PreparedStatement insercion = null;
		Statement update = null;
		PreparedStatement preparada = null;
		// PreparedStatement consulta = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/petclinic", "root", "Everis2017");
			if (connection != null)
				System.out.println("Conexión establecida");

			// insertaOwner(connection,
			// insercion,"Prueba","Prueba","Prueba","Prueba","Prueba");

			Owner o = new Owner();
			o.setFirstName("Prueba2");
			o.setLastName("Prueba2");
			o.setAddress("Prueba2");
			o.setCity("Prueba2");
			o.setTelephone("Prueba2");
			Pet p = new Pet();
			p.setName("Pepito");
			PetType pt = new PetType();
			pt.setId(5);
			p.setType(pt);
			p.setBirthDate(java.sql.Date.valueOf(java.time.LocalDate.now()));
			o.addPet(p);
			Reto(connection, insercion, o);
			// String city = "Sevilla";
			// updateCityOwner(connection, update, city);
			// getOwnerByNameLastName(connection, preparada, "George");

			// statement = connection.createStatement();
			// String sql = "select * from owners";
			// ResultSet rs = statement.executeQuery(sql);
			// while(rs.next()){
			// System.out.println("\nID: "+rs.getInt(1));
			// System.out.println("FIRST_NAME: "+rs.getString(2));
			// System.out.println("LAST_NAME: "+rs.getString(3));
			// System.out.println("ADDRESS: "+rs.getString(4));
			// System.out.println("CITY: "+rs.getString(5));
			// System.out.println("TELEPHONE: "+rs.getString(6));
			// }
			// rs.close();

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		} finally {
			try {
				if (statement != null)
					connection.close();
			} catch (SQLException se) {

			}
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

	public static void insertaOwner(Connection connection, PreparedStatement insercion, String nombre, String apellido,
			String direccion, String ciudad, String telefono) {

		String sqlInsert = "INSERT INTO `petclinic`.`owners` (`first_name`, `last_name`, `address`, `city`, `telephone`) VALUES (?,?,?,?,?);";
		try {
			insercion = connection.prepareStatement(sqlInsert);
			insercion.setString(1, nombre);
			insercion.setString(2, apellido);
			insercion.setString(3, direccion);
			insercion.setString(4, ciudad);
			insercion.setString(5, telefono);
			insercion.executeUpdate();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void updateCityOwner(Connection connection, Statement update, String city) {
		try {
			update = connection.createStatement();
			String updateCity = "update owners set city='" + city + "'where id =11";
			update.executeUpdate(updateCity);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int getOwnerByNameLastName(Connection connection, PreparedStatement get, String nombre) {
		int devuelve = 0;
		try {

			String consulta = "select * from owners where first_name = ? or last_name=?";
			get = connection.prepareStatement(consulta);
			get.setString(1, nombre);
			get.setString(2, nombre);

			ResultSet rs = get.executeQuery();

			while (rs.next()) {
				System.out.println("\nID: " + rs.getInt(1));
				System.out.println("FIRST_NAME: " + rs.getString(2));
				System.out.println("LAST_NAME: " + rs.getString(3));
				System.out.println("ADDRESS: " + rs.getString(4));
				System.out.println("CITY: " + rs.getString(5));
				System.out.println("TELEPHONE: " + rs.getString(6));

				devuelve = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return devuelve;
	}

	public static void Reto(Connection connection, PreparedStatement insercion, Owner o) {

		String sqlInsert = "INSERT INTO `petclinic`.`owners` (`first_name`, `last_name`, `address`, `city`, `telephone`) VALUES (?,?,?,?,?)";
		try {
			insercion = connection.prepareStatement(sqlInsert);
			insercion.setString(1, o.getFirstName());
			insercion.setString(2, o.getLastName());
			insercion.setString(3, o.getAddress());
			insercion.setString(4, o.getCity());
			insercion.setString(5, o.getTelephone());
			insercion.executeUpdate();
			System.out.println("Owner inserted.");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sqlInsertPet = "INSERT INTO `petclinic`.`pets` (`name`, `birth_date`,type_id,owner_id,breed_id) VALUES (?, ?,?,?,?)";
		try {
			insercion = connection.prepareStatement(sqlInsertPet);
			insercion.setString(1, o.getPets().get(0).getName());
			insercion.setDate(2, (java.sql.Date) o.getPets().get(0).getBirthDate());
			insercion.setInt(3, o.getPets().get(0).getType().getId());
			
			insercion.setInt(4, getOwnerByNameLastName(connection, insercion, o.getLastName()));
			insercion.setInt(5, 5);
			insercion.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sqlDeletePet = "delete from `petclinic`.`pets` where id=?";
		try {
			insercion = connection.prepareStatement(sqlDeletePet);
			insercion.setInt(1, o.getPets().get(0).getId());
			
			insercion.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sqlDeleteOwner = "delete from `petclinic`.`owners` where id=?";
		try {
			insercion = connection.prepareStatement(sqlDeleteOwner);
			insercion.setInt(1, getOwnerByNameLastName(connection, insercion, o.getLastName()));
			
			insercion.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
