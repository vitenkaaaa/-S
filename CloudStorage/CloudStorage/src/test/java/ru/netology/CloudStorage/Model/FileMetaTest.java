package ru.netology.CloudStorage.Model;


import java.time.LocalDateTime;

public class FileMetaTest {

    @Test
    public void testCreateFileMeta() {
        User user = new User(); // предполагается, что у User есть конструктор без параметров
        String filename = "test.txt";
        String filepath = "/files/test.txt";
        LocalDateTime uploadDate = LocalDateTime.of(2023, 10, 10, 12, 0);

        FileMeta fileMeta = new FileMeta(filename, filepath, uploadDate, user);

        assertNotNull(fileMeta);
        assertEquals(filename, fileMeta.getFilename());
        assertEquals(filepath, fileMeta.getFilepath());
        assertEquals(uploadDate, fileMeta.getUploadDate());
        assertEquals(user, fileMeta.getUser());
    }

    @Test
    public void testSettersAndGetters() {
        User user = new User();
        FileMeta fileMeta = new FileMeta();

        Long id = 1L;
        String filename = "file.txt";
        String filepath = "/path/file.txt";
        LocalDateTime date = LocalDateTime.now();

        fileMeta.setId(id);
        fileMeta.setFilename(filename);
        fileMeta.setFilepath(filepath);
        fileMeta.setUploadDate(date);
        fileMeta.setUser(user);

        assertEquals(id, fileMeta.getId());
        assertEquals(filename, fileMeta.getFilename());
        assertEquals(filepath, fileMeta.getFilepath());
        assertEquals(date, fileMeta.getUploadDate());
        assertEquals(user, fileMeta.getUser());
    }
}