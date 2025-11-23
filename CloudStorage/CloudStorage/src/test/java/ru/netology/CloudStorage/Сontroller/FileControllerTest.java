package ru.netology.CloudStorage.Сontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import ru.netology.CloudStorage.Controller.FileController;
import ru.netology.CloudStorage.Service.Storage.FileStorageService;
import java.io.IOException;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FileController.class)
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileStorageService fileStorageService;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testUploadFileSuccess() throws Exception {
        String filename = "test.txt";

        when(fileStorageService.saveFile(Mockito.any(MultipartFile.class))).thenReturn(filename);

        MockMultipartFile file = new MockMultipartFile(
                "file", "test.txt", "text/plain", "Hello World".getBytes());

        mockMvc.perform(multipart("/files/upload")
                        .file(file))
                .andExpect(status().isOk())
                .andExpect(content().string(filename));
    }

    @Test
    public void testUploadFileError() throws Exception {
        when(fileStorageService.saveFile(Mockito.any(MultipartFile.class)))
                .thenThrow(new IOException());

        MockMultipartFile file = new MockMultipartFile(
                "file", "test.txt", "text/plain", "Hello World".getBytes());

        mockMvc.perform(multipart("/files/upload")
                        .file(file))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error saving file"));
    }

    @Test
    public void testDownloadFileSuccess() throws Exception {
        String filename = "test.txt";
        byte[] data = "File content".getBytes();
        when(fileStorageService.loadFileAsBytes(filename)).thenReturn(data);

        mockMvc.perform(get("/files/download/{filename}", filename))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=\"" + filename + "\""))
                .andExpect(content().bytes(data));
    }

    @Test
    public void testDownloadFileNotFound() throws Exception {
        String filename = "notfound.txt";
        when(fileStorageService.loadFileAsBytes(filename)).thenThrow(new IOException());

        mockMvc.perform(get("/files/download/{filename}", filename))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void testDeleteFileSuccess() throws Exception {
        String filename = "delete.txt";

        Mockito.doNothing().when(fileStorageService).deleteFile(filename);

        mockMvc.perform(delete("/files/delete/{filename}", filename))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteFileError() throws Exception {
        String filename = "error.txt";

        doThrow(new IOException()).when(fileStorageService).deleteFile(filename);

        mockMvc.perform(delete("/files/delete/{filename}", filename))
                .andExpect(status().isInternalServerError());
    }
}