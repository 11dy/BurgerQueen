package app.product.subproduct;

import app.product.Product;

public class BurgerSet extends Product {
    private Hamburger hamburger;
    private Side side;
    private Drink drink;

    public BurgerSet(String name, int price, int kcal, Hamburger hamburger, Side side, Drink drink){
        super(name, price, kcal);
        this.hamburger = hamburger;
        this.side = side;
        this.drink = drink;
    }

    public BurgerSet(BurgerSet burgerSet){
        super(burgerSet.getName(), burgerSet.getPrice(), burgerSet.getKcal());
        this.hamburger = new Hamburger(burgerSet.hamburger);
        this.side = new Side(burgerSet.side);
        this.drink = new Drink(burgerSet.drink);
    }

// 생성자를 통해서 세트를 구성. setter는 제외한다.
    public Hamburger getHamburger() {
        return hamburger;
    }

    public Side getSide() {
        return side;
    }

    public Drink getDrink() {
        return drink;
    }

}
