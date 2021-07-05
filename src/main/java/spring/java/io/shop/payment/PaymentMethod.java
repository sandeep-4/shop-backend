package spring.java.io.shop.payment;

import org.springframework.stereotype.Component;

@Component
public abstract class PaymentMethod {
	public abstract PaymentType getJobType();

	public abstract String getAPIKey();

	public abstract TransmissitionInfo processPayment(String teamId, Integer amount, Integer currencyType, String userCarsId);
	
	public static enum PaymentType{
		STRIPE(1,"STRIPE"),
		PAYPAL(2,"PAYPAL");
		
		private int type;
		private String value;
		
		private PaymentType(int type, String value) {
			this.type = type;
			this.value = value;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
	
	public static enum currencyPaymentType{
		USD(1,"USD");
		private final int value;
		private final String type;
		private currencyPaymentType(int value, String type) {
			this.value = value;
			this.type = type;
		}
		public int getValue() {
			return value;
		}
		public String getType() {
			return type;
		}
		
		
		
		
	}

}
