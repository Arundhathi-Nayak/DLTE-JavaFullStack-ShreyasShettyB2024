package employee.filehandler;

import employee.middleware.entity.Address;
import employee.middleware.entity.Personal;
import employee.middleware.remote.Operations;

import java.io.*;
import java.util.ArrayList;

public class FileRepository implements Operations {
    public FileRepository() throws IOException {
        if (!new File("Personal.txt").exists()) {
            File personalFile = new File("Personal.txt");
            new ObjectOutputStream(new FileOutputStream(personalFile)).writeObject(new ArrayList<Personal>());
        }
        if (!new File("PermanentAddress.txt").exists()) {
            File permanentFile = new File("PermanentAddress.txt");
            new ObjectOutputStream(new FileOutputStream(permanentFile)).writeObject(new ArrayList<Address>());
        }
        if (!new File("TemporaryAddress.txt").exists()) {
            File temporaryFile = new File("TemporaryAddress.txt");
            new ObjectOutputStream(new FileOutputStream(temporaryFile)).writeObject(new ArrayList<Address>());
        }
    }

    @Override
    public void create(ArrayList<Personal> personal, ArrayList<Address> permanentAddress, ArrayList<Address> temporaryAddress) {
        try {
            ArrayList<Personal> personalArrayList = (ArrayList<Personal>) read()[0];
            FileOutputStream fileOutputStream = new FileOutputStream(new File("Personal.txt"));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            personalArrayList.addAll(personal);
            objectOutputStream.writeObject(personalArrayList);
            objectOutputStream.close();
            fileOutputStream.close();

            ArrayList<Address> permanentAddressList = (ArrayList<Address>) read()[1];
            FileOutputStream fileOutputStream1 = new FileOutputStream(new File("PermanentAddress.txt"));
            ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(fileOutputStream1);
            permanentAddressList.addAll(permanentAddress);
            objectOutputStream1.writeObject(permanentAddressList);
            fileOutputStream1.close();
            objectOutputStream1.close();

            ArrayList<Address> temporaryAddressList = (ArrayList<Address>) read()[2];
            FileOutputStream fileOutputStream3 = new FileOutputStream(new File("TemporaryAddress.txt"));
            ObjectOutputStream objectOutputStream3 = new ObjectOutputStream(fileOutputStream3);
            temporaryAddressList.addAll(temporaryAddress);
            objectOutputStream3.writeObject(temporaryAddressList);
            fileOutputStream3.close();
            objectOutputStream3.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object[] read() {
        Object[] objects = new Object[3];
        try {
            FileInputStream fileInputStream = new FileInputStream(new File("Personal.txt"));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            objects[0] = objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();

            FileInputStream fileInputStream1 = new FileInputStream(new File("PermanentAddress.txt"));
            ObjectInputStream objectInputStream1 = new ObjectInputStream(fileInputStream1);
            objects[1] = objectInputStream1.readObject();
            objectInputStream1.close();
            fileInputStream1.close();

            FileInputStream fileInputStream2 = new FileInputStream(new File("TemporaryAddress.txt"));
            ObjectInputStream objectInputStream2 = new ObjectInputStream(fileInputStream2);
            objects[2] = objectInputStream2.readObject();
            objectInputStream2.close();
            fileInputStream2.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return objects;
    }

    @Override
    public Object[] read(Long aLong) {
        return new Object[0];
    }

    @Override
    public Object[] filterAddress(String s, Object o) {
        Object[] objects = new Object[2];
        try {
            ArrayList<Address> filter;
            FileInputStream fileInputStream = new FileInputStream(new File("PermanentAddress.txt"));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            filter = (ArrayList<Address>) objectInputStream.readObject();
            objects[0] = filter.stream().filter(each -> each.getPincode().equals(o));
            FileInputStream fileInputStream1 = new FileInputStream(new File("Personal.txt"));
            ObjectInputStream objectInputStream1 = new ObjectInputStream(fileInputStream1);
            objects[1] = filter.stream().filter(each -> each.getEmployeeID().equals(filter.stream().filter(each1 -> each1.getPincode().equals(o))));
            objectInputStream1.close();
            fileInputStream1.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return objects;
    }
}
