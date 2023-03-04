package app;

import app.product.Product;
import app.product.ProductRepository;
import app.product.subproduct.BurgerSet;
import app.product.subproduct.Drink;
import app.product.subproduct.Hamburger;
import app.product.subproduct.Side;

import java.util.Scanner;

// 장바구니에 담긴 상품들을 저장하고 있어야 한다.
public class Cart {
    private Product[] items = new Product[0]; // 0으로 한 이유: 담을 때 마다 길이 늘려주기 위해서
    private Scanner scanner = new Scanner(System.in);
    private ProductRepository productRepository;

    private Menu menu;

    public Cart(ProductRepository productRepository, Menu menu){
        this.productRepository = productRepository;
        this.menu = menu;
    }


    public void printCart() {
        System.out.println("🧺 장바구니");
        System.out.println("-".repeat(60));
        printCartItemDetails();
        System.out.println("-".repeat(60));
        System.out.printf("합계 : %d원\n", calculateTotalPrice());

        System.out.println("이전으로 돌아가려면 엔터를 누르세요. ");
        scanner.nextLine();
    }

        //여기에 장바구니 상품들을 옵션 정보와 함께 출력
        protected void printCartItemDetails() {
            for (Product product : items) {
                if (product instanceof BurgerSet) {
                    BurgerSet burgerSet = (BurgerSet) product;
                    System.out.printf(
                            "  %s %6d원 (%s(케첩 %d개), %s(빨대 %s))\n",
                            product.getName(),
                            product.getPrice(),
                            burgerSet.getSide().getName(),
                            burgerSet.getSide().getKetchup(),
                            burgerSet.getDrink().getName(),
                            burgerSet.getDrink().hasStraw() ? "있음" : "없음"
                    );
                }
                else if (product instanceof Hamburger){
                    System.out.printf(
                            "  %-8s %6d원 (단품)\n",
                            product.getName(),
                            product.getPrice()
                    );
                }
                else if (product instanceof Side) {
                    System.out.printf(
                            "  %-8s %6d원 (케첩 %d개)\n",
                            product.getName(),
                            product.getPrice(),
                            ((Side) product).getKetchup()  //
                    );
                } else if (product instanceof Drink) {
                    System.out.printf(
                            "  %-8s %6d원 (빨대 %s)\n",
                            product.getName(),
                            product.getPrice(),
                            ((Drink) product).hasStraw() ? "있음" : "없음"  // 아래 설명 참고
                    );
                }

            }
        }

        protected int calculateTotalPrice() {
            int totalPrice = 0;
            for (Product product : items) {
                totalPrice += product.getPrice();
            }
            return totalPrice;
        }



    public void addToCart(int productId) {

        Product product = productRepository.findById(productId); // 메서드 호출/ 장바구니 기능 구현 2.2.1


        Product newProduct;
        if(product instanceof Hamburger) newProduct = new Hamburger((Hamburger) product);
        else if(product instanceof Side) newProduct = new Side((Side) product);
        else if(product instanceof Drink) newProduct = new Drink((Drink) product);
        else newProduct = product; // BurgerSet은 새로운 인스턴스를 만들 필요가 없다. composeSet()에서 이미 새로운 BurgerSet을 만들어 리턴

        chooseOption(newProduct);

        if(product instanceof Hamburger){
            Hamburger hamburger = (Hamburger) newProduct;
            if(hamburger.isBurgerSet()) newProduct = composeSet(hamburger);
        }


        chooseOption(product);

        //장바구니에 상품 추가
        Product[] newItems = new Product[items.length + 1];
        System.arraycopy(items, 0, newItems, 0, items.length);
        newItems[newItems.length-1] = newProduct;
        items = newItems;

        System.out.printf("[📣] %s를(을) 장바구니에 담았습니다.\n", product.getName());
    }

        //2.2.2 상품 옵션 설정
        private void chooseOption(Product product){
            String input;

            if(product instanceof Hamburger){
                System.out.printf(
                        "단품으로 주문하시겠어요? (1)_단품(%d원) (2)_세트(%d원)\n",
                        product.getPrice(),
                        ((Hamburger) product).getBurgerSetPrice()
                );
                input = scanner.nextLine();
                if(input.equals("2"))((Hamburger) product).setIsBurgerSet(true);
            }
            else if (product instanceof Side){
                System.out.println("케첩은 몇개가 필요하신가요?");
                input = scanner.nextLine();
                ((Side) product).setKetchup(Integer.parseInt(input));
            }
            else if(product instanceof Drink){
                System.out.println("빨대가 필요하신가요? (1)_예 (2)_아니오");
                input = scanner.nextLine();
                if(input.equals("2")) ((Drink) product).setHasStraw(false);
            }
        }
        private BurgerSet composeSet(Hamburger hamburger){
            System.out.println("사이드를 골라주세요");
            menu.printSides(false);

            String sideId = scanner.nextLine();
            Side side = (Side) productRepository.findById(Integer.parseInt(sideId));
            Side newSide = new Side(side);
            chooseOption(side);

            System.out.println("음료를 골라주세요.");
            menu.printDrinks(false);

            String drinkId = scanner.nextLine();
            Drink drink = (Drink) productRepository.findById(Integer.parseInt(drinkId));
            Drink newDrink = new Drink(drink);
            chooseOption(drink);

            String name = hamburger.getName() + "세트";
            int price = hamburger.getBurgerSetPrice();
            int kcal = hamburger.getKcal() + side.getKcal() + drink.getKcal();

            return new BurgerSet(name, price, kcal, hamburger, side, drink);

        }

}



