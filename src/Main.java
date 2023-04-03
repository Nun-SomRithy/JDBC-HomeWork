import model.JdbcImp;
import model.Topic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static JdbcImp jdbcImp;

    public static void main(String[] args) {

        showOption();
        jdbcImp = new JdbcImp();

    }

    private static   void  showOption(){

        Scanner input = new Scanner(System.in);
        int chooseOption=0;

        do {
            System.out.println("""
                    1.Insert into Topic
                    2.Update Topic by ID
                    3.Delete Record by ID
                    4.Select All Record 
                    5.Select Record by ID
                    6.Select Record by Name
                    7.Exits
                    """);
            System.out.print("PLease Choose Option(1->5) = ");
            chooseOption = input.nextInt();
            switch (chooseOption) {
                case 1 -> {
                    input.nextLine();
                    System.out.println("===============Insert Into Topic===============");
                    Topic topic = new Topic();
                    System.out.print("Enter Name :");
                    topic.setName(input.nextLine());
                    System.out.print("Enter Description :");
                    topic.setDescription(input.nextLine());
                    topic.setStatus(true);
                    System.out.println("============================");

                    System.out.println("Insert Successfully");
                    System.out.println("============================");
                    insertTopic(topic);
                    press();
                    break;
                }
                case 2 -> {
                    System.out.println("===============Update Topic by ID===============");
                    updateById();
                    press();
                     break;


                }

                case 3 -> {
                    System.out.println("===============Delete Record by ID===============");
                    deleteById();
                    press();
                    break;
                }
                case 4 -> {
                    System.out.println("===============Select All from Topic===============");
                    selectTopic();
                    press();
                    break;
                }
                case 5 -> {
                    System.out.println("===============Select by ID===============");
                    selectById();
                    press();
                    break;
                }
                case 6 -> {
                    System.out.println("===============Select by Name===============");
                    selectByName();
                    press();
                    break;
                }
                default -> System.out.println("Wrong inPut");
            }

        }while (chooseOption!=7);


    }
        private  static  void  updateById(){
            Scanner input = new Scanner(System.in);
            System.out.print("Enter ID to update: ");
            int updateId = input.nextInt();
            try (Connection con = new JdbcImp().dataSource().getConnection()) {
                input.nextLine();
                System.out.print("Enter new name: ");
                String upName = input.nextLine();

                System.out.print("Enter new description: ");
                String upDescription = input.nextLine();

                System.out.print("Enter new status (true or false): ");
                boolean upStatus = input.nextBoolean();

                String updateSql = "UPDATE topics SET name=?, description=?, status=? WHERE id=?";
                PreparedStatement statement =con.prepareStatement(updateSql);


                statement.setString(1,upName );
                statement.setString(2, upDescription);
                statement.setBoolean(3, upStatus);
                statement.setInt(4, updateId);

                // 3. Execute SQL statement
                int updatedRow = statement.executeUpdate();

                if (updatedRow == 0) {
                    System.out.println("================================");
                    System.out.println("The  ID was not found.");
                    System.out.println("================================");
                } else {
                    System.out.println("================================");
                    System.out.println(updatedRow + " Recorded has  updated.");
                    System.out.println("================================");
                }
            }catch (Exception e){
                e.printStackTrace();

            }
        }
        private  static  void deleteById(){
            Scanner input=new Scanner(System.in);
            System.out.print("Enter id to Select :");
            int deleteId=input.nextInt();

            try (Connection con = new JdbcImp().dataSource().getConnection()) {
                String selectSql = "DELETE  FROM  topics WHERE id=?";
                PreparedStatement statement = con.prepareStatement(selectSql);

                statement.setInt(1, deleteId);

                int tmp=statement.executeUpdate();
                if (tmp==0){
                    System.out.println("---------------------------------------------");
                    System.out.println("The id "+deleteId+ " not found !!!");
                    System.out.println("---------------------------------------------");

                }
                else{
                    System.out.println("---------------------------------------------");
                    System.out.println("U have delete this id : {" +deleteId + "} Successfully ");
                    System.out.println("---------------------------------------------");

                }
            } catch (Exception e) {

                e.printStackTrace();

            }

        }
        private static  void insertTopic(Topic topic){
            try(Connection con= new JdbcImp().dataSource().getConnection()){
                String insertSql ="INSERT INTO topics (name,description,status) VALUES (?,?,?)";
                PreparedStatement statement= con.prepareStatement(insertSql);
                statement.setString(1,topic.getName());
                statement.setString(2,topic.getDescription());
                statement.setBoolean(3,topic.getStatus());
                statement.executeUpdate();

            }catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        private static void selectTopic() {

            try (Connection con = new JdbcImp().dataSource().getConnection()) {
                // 1.Create SQL Statement
                String selectSql = "SELECT * FROM  topics";
                PreparedStatement statement = con.prepareStatement(selectSql);

                // 2.Execute SQL Statment
                ResultSet resultSet = statement.executeQuery();

                // 3.Process Result with ResultSet
                List<Topic> topics = new ArrayList<>();
                while (resultSet.next()) {
                    Integer id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    Boolean status = resultSet.getBoolean("status");
                    topics.add(new Topic(id, name, description, status));
                }
                if (topics.isEmpty()){
                    System.out.println("=========================================");
                    System.out.println("There are no record in database...!");
                    System.out.println("=========================================");
                }else {
                    for (Topic topic : topics) {
                        System.out.println();
                        System.out.println(topic);
                        System.out.println("---------------------------------------------------------------");


                    }
                }

            } catch (Exception e) {

                e.printStackTrace();

            }
        }

        private static  void selectById(){
        Scanner input=new Scanner(System.in);
            System.out.print("Enter id to Select :");
            int selectId=input.nextInt();

            try (Connection con = new JdbcImp().dataSource().getConnection()) {
                // 1.Create SQL Statement
                String selectSql = "SELECT * FROM  topics WHERE id=?";
                PreparedStatement statement = con.prepareStatement(selectSql);

                statement.setInt(1, selectId);


                // 2.Execute SQL Statment
                ResultSet resultSet = statement.executeQuery();

                // 3.Process Result with ResultSet
                List<Topic> topics = new ArrayList<>();
                while (resultSet.next()) {
                    Integer id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    Boolean status = resultSet.getBoolean("status");
                    topics.add(new Topic(id, name, description, status));


                }
                if (topics.size() == 0) {
                    System.out.println("================================");
                    System.out.println("The  ID was not found.");
                    System.out.println("================================");
                } else {
                    for (Topic topic : topics) {
                        System.out.println("=========================================================");
                        System.out.println(topic);
                        System.out.println("=========================================================");
                    }
                }
            } catch (Exception e) {

                e.printStackTrace();

            }

        }

        private  static String selectByName(){
        Scanner input=new Scanner(System.in);
            System.out.print("Enter Name to Select :");
            String selectName=input.nextLine();

            try (Connection con = new JdbcImp().dataSource().getConnection()) {
                // 1.Create SQL Statement
                String selectSql = "SELECT * FROM  topics WHERE LOWER(name) = LOWER(?)";
                PreparedStatement statement = con.prepareStatement(selectSql);

                statement.setString(1,selectName);


                // 2.Execute SQL Statment
                ResultSet resultSet = statement.executeQuery();

                // 3.Process Result with ResultSet
                List<Topic> topics = new ArrayList<>();
                while (resultSet.next()) {
                    Integer id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    Boolean status = resultSet.getBoolean("status");
                    topics.add(new Topic(id, name, description, status));


                }if (topics.size() == 0) {
                    System.out.println("================================");
                    System.out.println("The  Name  was not found.");
                    System.out.println("================================");
                } else {
                    for (Topic topic : topics) {
                        System.out.println("=========================================================");
                        System.out.println(topic);
                        System.out.println("=========================================================");
                    }
                }
            } catch (Exception e) {

                e.printStackTrace();

            }

            return selectName;
        }

        private static void press() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }




}