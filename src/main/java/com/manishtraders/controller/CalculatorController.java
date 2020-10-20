package com.manishtraders.controller;

import com.manishtraders.transformer.ExcelExtraction;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;

@RestController
@AllArgsConstructor
@RequestMapping("/calculus")
public class CalculatorController {

    private final String STORAGE_PATH = ".";

    @Autowired
    private final ExcelExtraction excelExtraction;

    @PostMapping("/import")
    public ResponseEntity<Resource> importExcel(@RequestParam("file") MultipartFile excelFile) throws IOException {

        InputStream fileInputStream = excelFile.getInputStream();
        File output = calculate(fileInputStream);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=output.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(new InputStreamResource(new FileInputStream(output)));
    }

    private File calculate(InputStream inputStream) {
        return excelExtraction.ExceltoObject(inputStream);
    }

    @PostMapping("/save")
    public String saveFile(@RequestParam("file") MultipartFile excelFile) throws IOException {
        File file = new File(".");
        String currDir = file.getAbsolutePath();
        String filename = excelFile.getOriginalFilename();
        String location = currDir.substring(0, currDir.length() - 1) + filename;

        InputStream fileInputStream = excelFile.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream(location);

        int ch = 0;
        while ((ch = fileInputStream.read()) != -1) {
            fileOutputStream.write(ch);
        }
        fileOutputStream.flush();
        fileOutputStream.close();

        return "excel file uploaded";
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadExcel(@PathVariable("filename") String filename, HttpServletResponse response) throws FileNotFoundException {
        String location = getCurrentDirectory()+  filename + ".xlsx";;
        File file1 = new File(location);

        InputStream inputStream = new FileInputStream(file1);
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filename + ".xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(inputStreamResource);
    }

    @GetMapping("/delete")
    public ResponseEntity<String> delete() throws IOException {
        File parentDir = new File(getCurrentDirectory());
        File[] files = parentDir.listFiles((dir, name) -> name.endsWith(".xlsx"));

        for (File file : Arrays.asList(files)) {
            FileUtils.forceDelete(file);
        }
        return ResponseEntity.ok().body("Deleted File successfully");
    }

    @GetMapping("/tally")
    public ResponseEntity<?> syncTally(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:9000/";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return ResponseEntity.ok().body(response);
    }

    private String getCurrentDirectory() {
        File file = new File(".");
        String path = file.getAbsolutePath();
        return path.substring(0, path.length() - 1);
    }

}
