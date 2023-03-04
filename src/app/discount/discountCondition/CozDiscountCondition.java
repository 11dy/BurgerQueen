package app.discount.discountCondition;

import app.discount.discountPolicy.DiscountPolicy;
import app.discount.discountPolicy.FixedRateDiscountPolicy;

import java.util.Scanner;

public class CozDiscountCondition implements DiscountCondition {
    private boolean isSatisfied;
//    private FixedRateDiscountPolicy fixedRateDiscountPolicy = new FixedRateDiscountPolicy(10);

    private DiscountPolicy discountPolicy;

    public CozDiscountCondition(DiscountPolicy discountPolicy){
        this.discountPolicy = discountPolicy;
    } // 인터페이스를 통해 공통된 메서드들을 추상화하여 추상메서드로 정의하였고, 인터페이스를
    // 타입으로 사용한 필드를 정의함으로써 다형성을 통해 구현 클래스의 객체를 할당받을 수 있다

    public boolean isSatisfied(){
        return isSatisfied;
    }

    private void setSatisfied(boolean satisfied) {
        isSatisfied = satisfied;
    }

    public void checkDiscountCondition() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("코드스테이츠 수강생이십니까? (1)_예 (2)_아니오");
        String input = scanner.nextLine();

        if(input.equals("1")) setSatisfied(true);
        else if(input.equals("2")) setSatisfied(false);
    }
    public int applyDiscount(int price){  // 할인 정책을 적용하여 실제 할인 금액을 리턴
//        return fixedRateDiscountPolicy.calculateDiscountedPrice(price);
        return discountPolicy.calculateDiscountedPrice(price);
    }

}
