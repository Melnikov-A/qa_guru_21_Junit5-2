import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;

import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class ZipParsingTest {

    private final ClassLoader cl = ZipParsingTest.class.getClassLoader();

    @DisplayName("Чтение и проверка содержимого PDF-файла, распакованного из архива формата Zip")
    @Test
    void pdfTest() throws Exception {
        try (InputStream zipStream = cl.getResourceAsStream("guru.zip");
             ZipInputStream zipInputStream = new ZipInputStream(Objects.requireNonNull(zipStream))) {

            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (zipEntry.getName().equals("samplepdf.pdf")) {
                    PDF pdf = new PDF(zipInputStream);
                    Assertions.assertTrue(pdf.text.contains("Lorem Ipsum is simply dummy"));
                }
            }
        }
    }

    @DisplayName("Чтение и проверка содержимого XLSX-файла, распакованного из архива формата Zip")
    @Test
    void xlsxTest() throws Exception {
        try (InputStream zipStream = cl.getResourceAsStream("guru.zip");
             ZipInputStream zipInputStream = new ZipInputStream(Objects.requireNonNull(zipStream))) {

            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (zipEntry.getName().equals("samplexlsx.xlsx")) {
                    XLS xls = new XLS(zipInputStream);
                    Assertions.assertEquals("Marcel",
                            xls.excel.getSheetAt(0).
                                    getRow(14)
                                    .getCell(1)
                                    .getStringCellValue());

                }
            }
        }
    }

    @DisplayName("Чтение и проверка содержимого CSV-файла, распакованного из архива формата Zip")
    @Test
    void csvTest() throws Exception {
        try (InputStream zipStream = cl.getResourceAsStream("guru.zip");
             ZipInputStream zipInputStream = new ZipInputStream(Objects.requireNonNull(zipStream))) {
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (zipEntry.getName().equals("phone.csv")) {
                    Reader reader = new InputStreamReader(zipInputStream);
                    CSVReader csvReader = new CSVReader(reader);

                    List<String[]> content = csvReader.readAll();
                    Assertions.assertEquals(4, content.size());
                    Assertions.assertArrayEquals(new String[]{"Phone", "Model"}, content.get(0));
                    Assertions.assertArrayEquals(new String[]{"iphone", "X"}, content.get(1));
                    Assertions.assertArrayEquals(new String[]{"Samsung", "s10"}, content.get(2));
                    Assertions.assertArrayEquals(new String[]{"Xiaomi", "Mi6"}, content.get(3));

                }
            }
        }
    }
}



