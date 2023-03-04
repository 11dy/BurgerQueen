package app.product;

import app.product.subproduct.Drink;
import app.product.subproduct.Hamburger;
import app.product.subproduct.Side;

public class ProductRepository {
    //필드로 배열 변수를 정의해서 모든 상품을 관리
    //배열에 담기는 타입이 다 다른 문제 > 다형성 활용
    //다형성: 상위 클래스 타입의 참조 변수로 하위클래스 타입의 객체를 할당하여 사용할 수 있는 것
    //나중에 컬렉션 프레임워크 대입
    private final Product[] products = {  // 프로그램 실행중 변경 x > 상수로 만들어둔다.
            new Hamburger(1, "새우버거", 3500, 500, false, 4500),
            new Hamburger(2, "치킨버거", 4000, 600, false, 5000),
            new Side(3, "감자튀김", 1000, 300, 1),
            new Side(4, "어니언링", 1000, 300, 1),
            new Drink(5, "코카콜라", 1000, 200, true),
            new Drink(6, "제로콜라", 1000, 0, true),
    };
    public Product[] getAllProducts(){
        return products;
    }
    public Product findById(int productId){
        for(Product product : products){
            if(product.getId() == productId) return product;
        }
        return null;
    }


}
