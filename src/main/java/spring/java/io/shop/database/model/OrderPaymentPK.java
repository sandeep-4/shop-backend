package spring.java.io.shop.database.model;

import java.io.Serializable;

public class OrderPaymentPK implements Serializable{

	private static final long serialVersionUID = 1L;

	private int orderId;
	
	private int paymentId;

	@Override
	public int hashCode() {
		int hash =3;
		hash=97 *hash+this.orderId;
		hash=97* hash+this.paymentId;
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		 if (obj == null) {
	            return false;
	        }
	        if (getClass() != obj.getClass()) {
	            return false;
	        }
	        final OrderPaymentPK other = (OrderPaymentPK) obj;
	        if (this.orderId != other.orderId) {
	            return false;
	        }
	        if (this.paymentId != other.paymentId) {
	            return false;
	        }
	        return true;
	    
	    
	}
	
	
	
	
}
