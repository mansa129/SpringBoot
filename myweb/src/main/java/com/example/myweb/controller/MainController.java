package com.example.myweb.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.myweb.model.User;
import com.example.myweb.service.UserService;

@Controller
public class MainController {
	
	private UserService userService;
	
	@GetMapping("/")			// 기본경로로 해당 메서드 실행
	public String home() {
		WriteLog wl = new WriteLog();
		wl.writeLog("홈페이지로 이동\n");
		return "index";			// index.html 파일 실행
	}
	
	@GetMapping("/board")      // localhost:8080/board <--접속 할 경우 동작
	public String boardPage(Model model) { 
//		model.addAttribute("users", userService.getAllUser());
//		return "board";        //board.html 파일로 이동
	return findPage(1, model);
	}
	
	@GetMapping("/board/new")
	public String signupPage(Model model) {
		User user = new User();
		model.addAttribute("user",user);          //model.addAttribute는 HTML로 값을 넘기는 역할을 함.
        return "signup";	//signup.html로 이동
	}
	
	@PostMapping("/board")
	public String saveUser(@ModelAttribute("user") User user) 
	{
		userService.saveUser(user);
		return "redirect:/board";       //board.html로 돌아가라
	}
	
	@GetMapping("/board/edit/{no}")
	public String editPage(@PathVariable Long no, Model model) {
		model.addAttribute("user",userService.getUserById(no));
		return "edit_user";  //edit_user.html로 이동시킴
	}
	
	@PostMapping("/board/{no}")
	public String editUser(@PathVariable Long no, @ModelAttribute("user") User user, Model model) {
		//번호(key)를 통해서 데이터베이스에서 정보 가져오기
		User db_user = userService.getUserById(no);
		db_user.setNo(no);
		db_user.setId(user.getId());
		db_user.setPw(user.getPw());
		
		userService.updateUser(db_user);
		return "redirect:/board";        //board.html로 돌아가라
	}
	
	@GetMapping("/board/{no}")
	public String deletUser(@PathVariable Long no) {
		userService.deleteUser(no);
		return "redirect:/board";
	}
	
	public MainController(UserService userService) {
		super();
		this.userService = userService;
	} 

	@GetMapping("/another")
	public String anotherPage(Model model) {
		WriteLog wl = new WriteLog();
		wl.writeLog("another페이지로 이동\n");
		String msg1 = wl.readText();
		model.addAttribute("send1", msg1);
		return "another";				// another.html 파일 실행
	}
	
	@GetMapping("/another/result")
	public String result(@RequestParam("input-name") String name, Model model) {
		// Model : Java -> HTML 송신
		// @RequestParam : HTML -> Java 수신
		model.addAttribute("send1", name);
		return "result";				// result.html 파일 실행
	}
	
	@GetMapping("/page/{pageNo}")
	public String findPage(@PathVariable(value = "pageNo") int pageNo, Model model) {
		int pageSize =8; //한 페이지에 보여줄 개수 지정
		Page<User> page = userService.findPaginated(pageNo, pageSize);
		List<User> listUsers = page.getContent();
		
		model.addAttribute("currentPage",pageNo);
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("totalItems",page.getTotalElements());
		model.addAttribute("users",listUsers);
		return "board";
	}
}

class WriteLog{
	public void writeLog(String page) {
		FileOutputStream fout = null;
		Calendar c = Calendar.getInstance();
		Date date = c.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
		String times = sdf.format(date);
		String msg = "["+times+"]" +page;
		
		byte byteString[] = msg.getBytes();
		try {
			fout = new FileOutputStream("C:\\java\\log.txt",true);
			fout.write(byteString);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
 	}
	
	public String readText() {
			FileInputStream fin = null;
			int read = -1;
			String str1 ="";
			try {
				fin = new FileInputStream("C:\\java\\info.txt");
				//한글깨짐 방지
				InputStreamReader reader = new InputStreamReader(fin, "UTF-8");
				BufferedReader in = new BufferedReader(reader);
				//파일 ==> byte(한글자), Java ==> 문자열(여러글자)
				while((read = in.read()) !=-1) {
					str1 += (char)read;
				}
				fin.close();
				System.out.println(str1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return str1;
		
		
	}
}

