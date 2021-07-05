package spring.java.io.shop.payment;

import com.stripe.model.Card;
import com.stripe.model.Customer;

public class UserCardInfo {

	private Customer customer;
	private Card card;
	

	
	public UserCardInfo() {
		super();
	}
	public UserCardInfo(Customer customer, Card card) {
		super();
		this.customer = customer;
		this.card = card;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Card getCard() {
		return card;
	}
	public void setCard(Card card) {
		this.card = card;
	}
	
	
	
	
}
