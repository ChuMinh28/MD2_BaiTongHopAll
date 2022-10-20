package ra.bussiness.implement;

import ra.bussiness.design.ICrud;
import ra.bussiness.entity.Users;
import ra.data.DataURL;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UsersImp implements ICrud<Users, Integer> {
    @Override
    public boolean create(Users users) {
        List<Users> listUsers = readFromFile();
        if (listUsers == null) {
            listUsers = new ArrayList<>();
        }
        listUsers.add(users);
        boolean check = writeToFile(listUsers);
        return check;
    }

    @Override
    public boolean update(Users users) {
        List<Users> listUsers = readFromFile();
        boolean returnData = false;
        for (int i = 0; i < listUsers.size(); i++) {
            if (listUsers.get(i).getUserID() == users.getUserID()) {
                listUsers.set(i,users);
                returnData = true;
                break;
            }
        }
        boolean result = writeToFile(listUsers);
        if (result && returnData) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        List<Users> listUsers = readFromFile();
        boolean returnData = false;
        for (int i = 0; i < listUsers.size(); i++) {
            if (listUsers.get(i).getUserID() == id) {
                listUsers.remove(i);
                returnData = true;
                break;
            }
        }
        boolean result = writeToFile(listUsers);
        if (result && returnData) {
            return true;
        }
        return false;
    }

    @Override
    public List<Users> findAll() {
        return readFromFile();
    }

    @Override
    public Users findById(Integer id) {
        List<Users> listUsers = readFromFile();
        for (Users user:listUsers) {
            if (user.getUserID()==id){
                return user;
            }
        }
        return null;
    }

    @Override
    public List<Users> readFromFile() {
        List<Users> listUsers = null;
        File file = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            file = new File(DataURL.URL_USERS_FILE);
            if (file.exists()){
                fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                listUsers = (List<Users>) ois.readObject();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return listUsers;
    }

    @Override
    public boolean writeToFile(List<Users> list) {
        File file = null;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        boolean returnData = true;
        try {
            file = new File(DataURL.URL_USERS_FILE);
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (fos != null){
                    fos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return returnData;
    }

    @Override
    public Users inputData(Scanner scanner) {
        List<Users> listUsers = readFromFile();
        if (listUsers == null) {
            listUsers = new ArrayList<>();
        }

        Users user = new Users();
        user.setUserID(listUsers.size()+1);
        System.out.print("Nhập tên đăng nhập: ");
        user.setUserName(scanner.nextLine());
        System.out.print("Nhập mật khẩu: ");
        user.setPassword(scanner.nextLine());
        String checkPassword;
        System.out.print("Nhập lại mật khẩu: ");
        do {
             checkPassword = scanner.nextLine();
             if (user.getPassword().equals(checkPassword)){
                 break;
             }else {
                 System.err.println("Mật khẩu không trùng khớp");
             }
        }while (true);
        System.out.print("Nhập tên người dùng: ");
        user.setFullName(scanner.nextLine());
        user.setPermission(false);
        System.out.println("Trạng thái người dùng: ");
        user.setUserStatus(Boolean.parseBoolean(scanner.nextLine()));
        listUsers.add(user);
        writeToFile(listUsers);
        return user;
    }

    @Override
    public void displayData() {
        List<Users> listUsers = readFromFile();
        String status;
        if (listUsers.get(1).isUserStatus()){
            status = "Hoạt động";
        }else {
            status = "Không hoạt động";
        }
        for (Users user:listUsers) {
            System.out.println("|----------------------------------------------|");
            System.out.println("|   ID người dùng: "+user.getUserID());
            System.out.println("|   Tên người dùng: "+user.getFullName());
            System.out.println("|   Trạng thái người dùng: "+status);
        }
    }
    public Users checkLogin(String userName, String password){
        List<Users> listUsers = readFromFile();
        for (Users user:listUsers) {
            if (user.getUserName().equals(userName) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }
}
