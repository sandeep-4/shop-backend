package spring.java.io.shop.payment;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stripe.Stripe;
import com.stripe.model.Card;
import com.stripe.model.Charge;
import com.stripe.model.Customer;

import spring.java.io.shop.configs.AppConfig;
import spring.java.io.shop.tracelogged.EventLogManager;

@Component
public class StripePayment extends PaymentMethod{

	@Autowired
	AppConfig appConfig;
	
	
	@Override
	public PaymentType getJobType() {
		return PaymentType.STRIPE;
	}

	@Override
	public String getAPIKey() {
		return appConfig.getValueOfKey("stripe.secretKey");
	}

	@Override
	public TransmissitionInfo processPayment(String teamId, Integer amount, Integer currencyType, String userCardId) {
		Stripe.apiKey=this.getAPIKey();
		TransmissitionInfo transmissitionInfo=new TransmissitionInfo();
		EventLogManager.getInstance().info("Make payment for team "+teamId);
		HashMap<String, Object> defaultChargeParams=new HashMap<String,Object>();
		
		
		defaultChargeParams.put("amount", PaymentManager.converterPriceByCurrency(amount, currencyType));
		defaultChargeParams.put("currency", PaymentManager.getCurrencyByType(currencyType));
		defaultChargeParams.put("customer",userCardId);
		
		Charge createCharge=null;
		try {
		createCharge=Charge.create(defaultChargeParams);
		if(createCharge!=null) {
			String transmisstionInfo=createCharge.getId();
			transmissitionInfo.setTransmissitionId(transmisstionInfo);
			String cardInfo="";
			Customer customer=Customer.retrieve(userCardId) ;
			Card card=customer.getCards().retrieve(customer.getDefaultCard());
			cardInfo=card.getType()+"-"+card.getLast4();
			transmissitionInfo.setCardInfo(cardInfo);
			
		}
		
		
		} catch (Exception e) {
			EventLogManager.getInstance().error(e);
			return null;
		}
		return transmissitionInfo;
		
	}

}
