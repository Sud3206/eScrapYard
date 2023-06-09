package com.scrap.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scrap.pojos.Admin;
import com.scrap.pojos.Register;
import com.scrap.services.AdminService;
import com.scrap.services.RegisterService;

@CrossOrigin
@RestController
@RequestMapping
public class AdminController {
	@Autowired
	private AdminService adminService;

	@Autowired
	private RegisterService registerService;

//    ResponseEntity is a class in Spring Framework that represents an HTTP response.
//	It is used to wrap the response data, HTTP status code, and headers into a single 
//	object that can be returned from a Spring controller method.
	// get all admins
	@GetMapping("/getAll")
	public ResponseEntity<?> findAll() {
		try {
			List<Admin> list = adminService.findAll();
			Iterator<Admin> itr = list.iterator();
			for (Admin admin : list) {
				System.out.println(admin);
			}

//			if (list.isEmpty())
			if (list != null)
				return Response.status(HttpStatus.NOT_FOUND);
			return Response.success(list);

		} catch (Exception e) {
			return Response.error(e.getMessage());
		}
	}

	// admin login
	@PostMapping("/adminlogin")
	public ResponseEntity<?> authenticateAdmin(@RequestBody Admin admin) {
		try {
			Admin admin1 = adminService.authenticateAdmin(admin);
			if (admin1 == null)
//				return Response.success("Not found...!!");
				return Response.status(HttpStatus.NOT_FOUND);
			return Response.success(admin1);
		}

		catch (Exception e) {
			return Response.error(e.getMessage());
		}
	}

//when admin will click on forget pw ,email id will be checked in th DB table
	@GetMapping("/forgetPwd")
	public ResponseEntity<?> forgetPassword(@RequestBody String email) {
		try {
			Admin admin1 = adminService.findByEmail(email);
			if (admin1 == null)
				return Response.status(HttpStatus.NOT_FOUND);
			return Response.success(admin1);
		} catch (Exception e) {
			return Response.error(e.getMessage());
		}
	}

//set new pwd
	@PutMapping("/setPwd")
	public ResponseEntity<?> update(@RequestBody Admin admin) {
		admin.setEmail(admin.getEmail());
		admin.setPassword(admin.getPassword());
		Admin admin1 = adminService.save(admin);
		if (admin1 == null)
			return Response.status(HttpStatus.NOT_FOUND);
		return Response.success(admin1);
	}

	// admin can view list of users as per the role by selecting a particular role
	@GetMapping("/findByRole/{role}")
	public ResponseEntity<?> findByRole(@PathVariable String role) {
		List<Register> list = registerService.findByRole(role);
		if (list.isEmpty())
			return Response.status(HttpStatus.NOT_FOUND);
		return Response.success(list);
	}

	// admin can delete a user
	@DeleteMapping("/admin/{id}")
	public ResponseEntity<?> deleteById(@PathVariable int id) {
		int noOfRowsDeleted = registerService.deleteById(id);
		if (noOfRowsDeleted == 0)
			return Response.status(HttpStatus.NOT_FOUND);
		return Response.success("no. of rows deleted is " + noOfRowsDeleted);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> exceptionHandler(Exception e) {
		System.out.println("Congrats...Exception found....!!!");
		return Response.error(e.getMessage());
	}

}
