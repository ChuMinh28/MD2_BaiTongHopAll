package ra.run;

import ra.bussiness.entity.Users;
import ra.bussiness.implement.CatalogImp;
import ra.bussiness.implement.ProductImp;
import ra.bussiness.implement.UsersImp;
import ra.bussiness.notifications.ShopMessage;


import java.util.Scanner;

public class ShopManagement {
    static Scanner scanner = new Scanner(System.in);
    private final static UsersImp usersImp = new UsersImp();
    private final static CatalogImp catalogImp = new CatalogImp();
    private final static ProductImp productImp = new ProductImp();

    public static void main(String[] args) {
        Users userAdmin = new Users(0,"minhchuquang","123456","chu quang minh",true,true);
        do {
            System.out.println("|-----------------------------------------------------|");
            System.out.println("|                      TOY STORE                      |");
            System.out.println("|-----------------------------------------------------|");
            System.out.println("|                     1.Đăng nhập                     |");
            System.out.println("|                     2.Đăng ký                       |");
            System.out.println("|                     3.Thoát                         |");
            System.out.println("|-----------------------------------------------------|");

            int choice1 = Integer.parseInt(scanner.nextLine());
            switch (choice1){
                case 1:
                    logIn(scanner);
                    break;
                case 2:
                    resister();
                    break;
                case 3:
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Vui lòng chọn từ 1 - 3");
            }
        }while (true);
    }
    public static void logIn(Scanner scanner){
        do {
            System.out.print("Nhập tên đăng nhập: ");
            String account = scanner.nextLine();
            System.out.print("Nhập mật khẩu: ");
            String password = scanner.nextLine();
            Users user = usersImp.checkLogin(account,password);
            if (user!=null){
                //Đăng nhập thành công
                if (user.isPermission()){
                    //Tài khoản admin
                    displayMenuShopManagement();
                }else {
                    //Tài khoản user
                    menuCustomer(user);
                }
                break;
            }else {
                // Đăng nhập thất bại
                System.out.println("|-----------------------------------------------------|");
                System.err.println("|              Sai tên đăng nhập hoặc mật khẩu        |");
                System.out.println("|-----------------------------------------------------|");
                System.out.println("|                  1.Đăng nhập lại                    |");
                System.out.println("|                  2.Bạn chưa có tài khoản            |");
                System.out.println("|                  3.Thoát                            |");
                System.out.println("|-----------------------------------------------------|");
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice == 2){
                    // Chuyển đến menu đăng ký
                    resister();
                }else if (choice == 3){
                    break;
                }
            }
        }while (true);
    }
    public static void displayMenuShopManagement(){
        boolean exitMenuShopManagement = true;
        do {
            System.out.println("|-----------------------------------------------------|");
            System.out.println("|                    QUẢN LÝ CỬA HÀNG                 |");
            System.out.println("|-----------------------------------------------------|");
            System.out.println("|                  1.Quản lý danh mục                 |");
            System.out.println("|                  2.Quản lý sản phẩm                 |");
            System.out.println("|                  3.Quản lý người dùng               |");
            System.out.println("|                  4.Đăng xuất                        |");
            System.out.println("|-----------------------------------------------------|");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice){
                case 1:
                    menuCatalogManagement();
                    break;
                case 2:
                    productManagement();
                    break;
                case 3:
                    usersManagement();
                    break;
                case 4:
                    exitMenuShopManagement = false;
                    break;
                default:
                    System.err.println("vui lòng chọn từ 1-4");
            }
        }while (exitMenuShopManagement);
    }
    public static void menuCatalogManagement(){
        boolean exitMenuCatalogManagement = true;
        do {
            System.out.println("|-----------------------------------------------------|");
            System.out.println("|                   QUẢN LÝ DANH MỤC                  |");
            System.out.println("|-----------------------------------------------------|");
            System.out.println("|                 1.Danh sách danh mục                |");
            System.out.println("|                 2.Thêm mới danh mục                 |");
            System.out.println("|                 3.Cập nhật danh mục                 |");
            System.out.println("|                 4.Xóa danh mục                      |");
            System.out.println("|                 5.Tìm kiếm danh mục theo tên        |");
            System.out.println("|                 6.Thoát                             |");
            System.out.println("|-----------------------------------------------------|");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice){
                case 1:
                    catalogImp.displayData();
                    break;
                case 2:
                    catalogImp.inputData(scanner);
                    break;
                case 3:
                case 4:
                    System.out.print("Nhập vào mã danh mục cần xóa: ");
                    int catID = Integer.parseInt(scanner.nextLine());
                    catalogImp.delete(catID);
                    break;
                case 5:
                    System.out.print("Nhập tên danh mục cần tìm kiếm: ");
                    String findByName = scanner.nextLine();
                    catalogImp.findCatalogByName(findByName);
                    break;
                case 6:
                    exitMenuCatalogManagement = false;
                    break;
                default:
                    System.err.println(ShopMessage.NOTIFY_CATALOG_MANAGEMENT_CHOICE);
            }
        }while (exitMenuCatalogManagement);
    }
    public static void productManagement(){
        boolean exitProductManagement = true;
        do {
            System.out.println("|-----------------------------------------------------|");
            System.out.println("|                    QUẢN LÝ SẢN PHẨM                 |");
            System.out.println("|                 1.Danh sách sản phẩm                |");
            System.out.println("|                 2.Thêm mới sản phẩm                 |");
            System.out.println("3.Cập nhật sản phẩm");
            System.out.println("4.Xóa sản phẩm");
            System.out.println("5.Tìm kiếm sản phẩm theo tên");
            System.out.println("6.Thoát");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice){
                case 1:
                    productImp.displayData();
                    break;
                case 2:
                    productImp.inputData(scanner);
                    break;
                case 3:
                case 4:
                    System.out.println("Nhập mã danh mục cần xóa: ");
                    int idDelete = Integer.parseInt(scanner.nextLine());
                    productImp.delete(idDelete);
                    break;
                case 5:
                    System.out.println("Nhạp tên danh mục cần tìm kiếm:");
                    String findByName = scanner.nextLine();
                    catalogImp.findCatalogByName(findByName);
                    break;
                case 6:
                    exitProductManagement = false;
                    break;
                default:
                    System.err.println(ShopMessage.NOTIFY_CATALOG_MANAGEMENT_CHOICE);
            }
        }while (exitProductManagement);
    }
    public static void usersManagement(){
        boolean exitUsersManagement = true;
        do {
            System.out.println("*****************QUẢN LÝ NGƯỜI DÙNG*****************");
            System.out.println("1.Danh sách người dùng");
            System.out.println("2.Thêm mới người dùng");
            System.out.println("3.Cập nhật người dùng");
            System.out.println("4.Xóa người dùng");
            System.out.println("5.Tìm người dùng theo tên đăng nhập");
            System.out.println("6.Thoát");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice){
                case 1:
                    usersImp.displayData();
                    System.out.println("|----------------------------------------------|");
                    break;
                case 2:
                    usersImp.inputData(scanner);
                    break;
                case 3:

                case 4:
                    usersImp.displayData();
                    System.out.println("Nhập mã người dùng cần xóa:");
                    int deleteUser = Integer.parseInt(scanner.nextLine());
                    usersImp.delete(deleteUser);
                    break;
                case 5:
                    System.out.println("Nhập tên người dùng muốn tìm kiếm:");
                case 6:
                    exitUsersManagement = false;
                    break;
                default:
                    System.err.println(ShopMessage.NOTIFY_CATALOG_MANAGEMENT_CHOICE);
            }
        }while (exitUsersManagement);
    }
    public static void menuCustomer(Users users){
        boolean exitMenuCustomer = true;
        do {
            System.out.printf("**************** CHÀO MỪNG %s ****************",users.getUserName().toUpperCase());
            System.out.println("1.Mua sản phẩm");
            System.out.println("2.Giỏ hàng khách hàng");
            System.out.println("3.Phản hồi khách hàng");
            System.out.println("4.Đăng xuất");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice){
                case 1:
                case 2:
                case 3:
                case 4:
                    exitMenuCustomer = false;
                    break;
                default:
                    System.err.println("Vui lòng chọn từu 1-4");
            }
        }while (exitMenuCustomer);
    }
    public static void resister(){
        UsersImp userNew = new UsersImp();
        userNew.inputData(scanner);
    }
}
