package com.scrap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scrap.dtos.OrderDto;
import com.scrap.pojos.Order;
import com.scrap.services.OrderService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/orders")
public class OrderController
{
	@Autowired
	private OrderService orderService;

	

	// user can cancel his order ...findby id in order table.
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable int id)
	{
		try
		{
			int noOfRowsDeleted = orderService.deleteById(id);
			if (noOfRowsDeleted == 0)
				return Response.status(HttpStatus.NOT_FOUND);
			return Response.success("no. of rows deleted is " + noOfRowsDeleted);
		} catch (Exception e)
		{
			return Response.error(e.getMessage());
		}
	}

	// to find list of orders placed by a user
	@GetMapping("/listOfOrderPlaced/{id}")
	public ResponseEntity<?> findListOfOrder(@PathVariable("id") int id)
	{

		List<Order> listOfOrders = orderService.findListOfOrder(id);

		return Response.success(listOfOrders);

	}
	
	// buyer place order(industrialist or trader can buy product and place the
		// orders.)
		@PostMapping("/placeOrder")
		public ResponseEntity<?> placeOrder(@RequestBody OrderDto order)
		{
			try
			{
				Order order1 = orderService.placeOrder(order);
				if (order1 == null)
					return Response.status(HttpStatus.NOT_FOUND);
				return Response.success(order1);
			} catch (Exception e)
			{
				return Response.error(e.getMessage());
			}
		}
	
	

}