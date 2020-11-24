//package com.eksamengr2.alpha.mappers;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class EditProjectMapper {
//
//    //Deklarationer
//    public Daters getDaterForShowDaters(String username) throws SQLException {
//        Daters search1=null;
//
//        Connection conn = null; //forbindelse
//        ResultSet resultSet = null; //dataflow 1 linie ad gangen
//        List<Daters> arrDaters = new ArrayList<>();
//        PreparedStatement preparedStatement=null;
//
//
//        try {
//            // 1) Connect to the database
//            conn = DriverManager.getConnection("jdbc:mysql://den1.mysql1.gear.host:3306/miniprojektgrup1?autoReconnect=true&useSSL=false", "miniprojektgrup1", "Lo4z1aY~!bQN");
//
//            //2) Create a string that holds the query with ? as user input
//            String sqlString = "SELECT * FROM users WHERE username=? ";
//
//            //3) prepare the query
//            preparedStatement= conn.prepareStatement(sqlString);
//
//            //4 Indsætter værdier i placeholders '?'
//            preparedStatement.setString(1, username); //TEST
//
//            //5) excecuter sql statement
//            resultSet = preparedStatement.executeQuery();
//
//            //6) Fylder ArrayList med data fra mySQL database tabel "Person"
//            while (resultSet.next()) //true så længe der er mere data i sql tabel
//            {
//                //BEAN Daters(String userName, LocalDate birthday, String profileText, int gender, String region, String pictureLink)
//                //laver et object med en resultat række
//                search1 = new Daters(   resultSet.getString("username"),
//                        resultSet.getDate("birthday").toLocalDate(),
//                        resultSet.getString("profiletext"),
//                        resultSet.getInt("gender"),
//                        resultSet.getString("region"),
//                        resultSet.getString("picture"));
//
//                //7) fylder ArrayList med data
//                //arrDaters.add(search1);
//            }
//            //return arrResultList;
//        }//try
//        catch (Exception e) {
//            System.err.println(e.getMessage());
//
//        } finally //kode der skal køres selvom den bugger (lukke connection)
//        {
//            if (preparedStatement != null)
//                preparedStatement.close();
//
//            if (conn != null)
//                conn.close();
//        }
////        //TEST PRINT
////        for (Daters a : arrDaters ) { System.out.println(a.getUserName() +" : "+ a.getBirthday()) ; }
//
//        return search1;
//    }//metode
//}
