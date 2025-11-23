package ru.netology.CloudStorage.Model;


import java.util.HashSet;
import java.util.Set;

public class UserTest {

    @Test
    public void testCreateUser() {
        String username = "testuser";
        String password = "password123";

        User user = new User(username, password);

        assertNotNull(user);
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertNull(user.getId());
        assertNull(user.getFiles());
    }

    @Test
    public void testSettersAndGetters() {
        User user = new User();

        Long id = 1L;
        String username = "anotherUser";
        String password = "pass";

        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);

        Set<FileMeta> files = new HashSet<>();
        user.setFiles(files);

        assertEquals(id, user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(files, user.getFiles());
    }

    @Test
    public void testAddFilesToUser() {
        User user = new User("user", "pass");
        FileMeta file1 = new FileMeta("file1.txt", "/path/file1.txt", java.time.LocalDateTime.now(), user);
        FileMeta file2 = new FileMeta("file2.txt", "/path/file2.txt", java.time.LocalDateTime.now(), user);

        Set<FileMeta> files = new HashSet<>();
        files.add(file1);
        files.add(file2);

        user.setFiles(files);

        assertEquals(2, user.getFiles().size());
        assertTrue(user.getFiles().contains(file1));
        assertTrue(user.getFiles().contains(file2));
    }
}