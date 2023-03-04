package app;

import app.discount.Discount;
import app.discount.discountCondition.CozDiscountCondition;
import app.discount.discountCondition.DiscountCondition;
import app.discount.discountCondition.KidDiscountCondition;
import app.discount.discountPolicy.FixedAmountDiscountPolicy;
import app.discount.discountPolicy.FixedRateDiscountPolicy;
import app.product.ProductRepository;

public class AppConfigurer {  //프로그램 동작에 필요한 모든 객체 생성,
    // 각 객체의 동작에 필요한 다른 객체들을 결정해주는 역할
    private Cart cart = new Cart(productRepository(), menu());

    public ProductRepository productRepository(){
        return new ProductRepository();
    }

    public Menu menu() {
        return new Menu(productRepository().getAllProducts());
    }

    public Cart cart(){
        return cart;
    }

    public Discount discount() {
        return new Discount(
                new DiscountCondition[]{
                        new CozDiscountCondition(new FixedRateDiscountPolicy(10)),
                        new KidDiscountCondition(new FixedAmountDiscountPolicy(500))
                }
        );
    }

    public Order order(){
        return new Order(cart(), discount());
    }
}
