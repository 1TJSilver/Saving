import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(100, 3, 10, 500.59);
        GameProgress gameProgress2 = new GameProgress(120, 3, 12, 480.67);
        GameProgress gameProgress3 = new GameProgress(50, 1, 1, 750);
        if (saveGame("Game1", gameProgress1)){
            System.out.println("Game save");
        }
        if (saveGame("Game2", gameProgress2)){
            System.out.println("Game save");
        }
        if (saveGame("Game3", gameProgress3)){
            System.out.println("Game save");
        }
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Games//savegames//Game1");
        arrayList.add("Games//savegames//Game2");
        arrayList.add("Games//savegames//Game3");
        zipFiles("Games//savegames//zip.zip", arrayList);
        deleteSave("Game1");
        deleteSave("Game2");
        deleteSave("Game3");
    }
    public static boolean saveGame (String name, GameProgress gameProgress){
        File file = new File("Games//savegames//" + name);
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)){
            oos.writeObject(gameProgress);
        } catch (IOException ex){
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }
    public static void zipFiles(String zipPath, ArrayList<String> listSave){
        for (String pathSave : listSave) {
            try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath));
                 FileInputStream fis = new FileInputStream(pathSave)) {


                ZipEntry zipEntry = new ZipEntry(pathSave + "_packed");
                zos.putNextEntry(zipEntry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zos.write(buffer);
                zos.closeEntry();

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    public static void deleteSave (String saveName) {
        File file = new File("Games\\savegames\\" + saveName);
        if (file.delete()){
            System.out.println("Save was deleted");
        }
    }
}
