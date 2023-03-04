package app.discount.discountCondition;

public interface DiscountCondition {
    //외부에서 사용하는 메서드들을 추상 메서드로 정의
    void checkDiscountCondition();
    int applyDiscount(int price);
    boolean isSatisfied();
}
