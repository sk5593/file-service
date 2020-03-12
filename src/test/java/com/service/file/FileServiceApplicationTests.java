package com.service.file;

import com.service.file.controller.ListController;
import com.service.file.entity.FileEntity;
import com.service.file.util.JdbcUtils;
import net.bytebuddy.asm.Advice;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class FileServiceApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
	void getList() throws SQLException, ClassNotFoundException {

    }

}
