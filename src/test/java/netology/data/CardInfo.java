package netology.data;

import lombok.Value;

@Value
public class CardInfo {
    String cardNumber;
    String month;
    String year;
    String owner;
    String cvc;
}
