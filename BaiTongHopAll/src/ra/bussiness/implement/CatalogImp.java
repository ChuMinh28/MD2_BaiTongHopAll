package ra.bussiness.implement;

import ra.bussiness.design.ICrud;
import ra.bussiness.entity.Catalog;
import ra.data.DataURL;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CatalogImp implements ICrud<Catalog, Integer> {
    @Override
    public boolean create(Catalog catalog) {
        List<Catalog> listCatalog = readFromFile();
        if (listCatalog == null) {
            listCatalog = new ArrayList<>();
        }
        listCatalog.add(catalog);
        boolean result = writeToFile(listCatalog);
        return result;
    }

    @Override
    public boolean update(Catalog catalog) {
        List<Catalog> listCatalog = readFromFile();
        boolean returnData = false;
        for (int i = 0; i < listCatalog.size(); i++) {
            if (listCatalog.get(i).getCatalogID() == catalog.getCatalogID()) {
                //thực hiện cập nhật
                listCatalog.set(i, catalog);
                returnData = true;
                break;
            }
        }
        boolean result = writeToFile(listCatalog);
        if (result && returnData){
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        List<Catalog> listCatalog = readFromFile();
        boolean returnData = false;
        for (int i = 0; i < listCatalog.size(); i++) {
            if (listCatalog.get(i).getCatalogID() == id) {
                listCatalog.remove(i);
                returnData = true;
                break;
            }
        }
        boolean result = writeToFile(listCatalog);
        if (result && returnData) {
            return true;
        }
        return false;
    }

    @Override
    public List<Catalog> findAll() {
        return readFromFile();
    }

    @Override
    public Catalog findById(Integer id) {
        List<Catalog> listCatalog = readFromFile();
        for (Catalog cat:listCatalog) {
            if (cat.getCatalogID() == id) {
                return cat;
            }
        }
        return null;
    }

    @Override
    public List<Catalog> readFromFile() {
        List<Catalog> listCatalog = null;
        File file = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            file = new File(DataURL.URL_CATALOG_FILE);
            if (file.exists()) {
                fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                listCatalog = (List<Catalog>) ois.readObject();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return listCatalog;
    }

    @Override
    public boolean writeToFile(List<Catalog> list) {
        File file = null;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        boolean returnData = true;
        try {
            file = new File(DataURL.URL_CATALOG_FILE);
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
        }catch (Exception e){
            returnData = false;
            e.printStackTrace();
        }finally {
            try {
                if (fos != null) {
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
    public Catalog inputData(Scanner scanner) {
        List<Catalog> listCatalog = readFromFile();
        if (listCatalog == null) {
            listCatalog = new ArrayList<>();
        }
        //khởi tạo đối tượng để nhập thông tin
        Catalog catNew = new Catalog();
        System.out.println("Nhập vào mã danh mục:");
        do {
            try {
                catNew.setCatalogID(Integer.parseInt(scanner.nextLine()));
                boolean checkExit = false;
                for (Catalog cat:listCatalog) {
                    if (cat.getCatalogID() == catNew.getCatalogID()) {
                        checkExit = true;
                        break;
                    }
                }
                if (!checkExit){
                    break;
                }else {
                    System.err.println("Mã sản phẩm đã tồn tại");
                }
            }catch (NumberFormatException e){
                System.err.println("Sai định dạng");
            }
        }while (true);
        System.out.println("Nhập vào tên danh mục");
        catNew.setCatalogName(scanner.nextLine());
        System.out.println("Nhập vào trạng thái danh mục");
        catNew.setCatalogStatus(Boolean.parseBoolean(scanner.nextLine()));
        listCatalog.add(catNew);
        writeToFile(listCatalog);
        return catNew;
    }

    @Override
    public void displayData() {
        List<Catalog> listCatalog = readFromFile();
        for (Catalog cat:listCatalog) {
            System.out.printf("Mã danh mục: %d\n",cat.getCatalogID());
            System.out.printf("Tên danh mục: %s\n",cat.getCatalogName());
            System.out.printf("Trạng thái danh mục: %b\n",cat.isCatalogStatus());
        }
    }

    public List<Catalog> findCatalogByName(String catName){
        List<Catalog> listCatalogFull = readFromFile();
        List<Catalog> listCatalog = new ArrayList<>();
        for (Catalog cat:listCatalogFull) {
            if (cat.getCatalogName().contains(catName)){
                listCatalog.add(cat);
            }
        }
        return listCatalog;
    }
}
