let cartService;

class ShoppingCartService {

    cart = {
        items:[],
        total:0
    };

    addToCart(productId)
    {
        const url = `${config.baseUrl}/cart/products/${productId}`;
        // const headers = userService.getHeaders();

        axios.post(url, {})// ,{headers})
            .then(()=> {
                this.loadCart()
                this.updateCartDisplay()
                const data = {
                    message: "Product added to cart successfully!"
                };
                templateBuilder.append("message", data, "errors")
            })
            .catch(error => {

                const data = {
                    error: "Add to cart failed."
                };

                templateBuilder.append("error", data, "errors")
            })
    }

    setCart(data)
    {
        this.cart = {
            items: [],
            total: 0
        }

        this.cart.total = data.total;

        for (const [key, value] of Object.entries(data.items)) {
            this.cart.items.push(value);
        }
    }

    loadCart()
    {

        const url = `${config.baseUrl}/cart`;

        axios.get(url)
            .then(response => {
                this.setCart(response.data)

                this.updateCartDisplay()

            })
            .catch(error => {

                const data = {
                    error: "Load cart failed."
                };

                templateBuilder.append("error", data, "errors")
            })

    }

    placeOrder(){
        const url = `${config.baseUrl}/orders`;

        axios.post(url)
            .then(response => {
                this.cart = {
                    items: [],
                    total: 0
                };
                this.updateCartDisplay();
                this.loadCartPage();
                const data = {
                    message: "Order successfully placed!"
                };
                templateBuilder.append("message", data, "errors")
            })
            .catch(error => {

                const data = {
                    error: "Order placement failed."
                };

                templateBuilder.append("error", data, "errors")
            })
    }

    loadCartPage()
    {
        templateBuilder.build("cart", this.cart, "main", () => {
            const itemList = document.getElementById("cart-item-list");
            this.cart.items.forEach(item => {
                this.buildItem(item, itemList)
            });

            const total = document.getElementById("cart-total");
            total.innerText = `$${this.cart.total.toFixed(2)}`;
        });
    }
    buildItem(item, parent)
    {
        let outerDiv = document.createElement("div");
        outerDiv.classList.add("cart-item");

        let photoDiv = document.createElement("div");
        photoDiv.classList.add("photo");
        let img = document.createElement("img");
        img.src = `/images/products/${item.product.imageUrl}`;
        img.addEventListener("click", () => {
            showImageDetailForm(item.product.name, img.src);
        });
        photoDiv.appendChild(img);
        outerDiv.appendChild(photoDiv);

        let detailsDiv = document.createElement("div");
        detailsDiv.classList.add("cart-item-details");
        outerDiv.appendChild(detailsDiv);

        let h4 = document.createElement("h4");
        h4.classList.add("cart-item-name");
        h4.innerText = item.product.name;
        detailsDiv.appendChild(h4);

        let descriptionDiv = document.createElement("div");
        descriptionDiv.classList.add("cart-item-desc");
        descriptionDiv.innerText = item.product.description;
        detailsDiv.appendChild(descriptionDiv);

        let priceH4 = document.createElement("h4");
        priceH4.classList.add("cart-item-price");
        priceH4.innerText = `$${item.product.price}`;
        detailsDiv.appendChild(priceH4);

        let quantityDiv = document.createElement("div");
        quantityDiv.classList.add("cart-item-qty");
        quantityDiv.innerText = `Quantity: ${item.quantity}`;
        detailsDiv.appendChild(quantityDiv);

        parent.appendChild(outerDiv);
    }

    clearCart()
    {

        const url = `${config.baseUrl}/cart`;

        axios.delete(url)
             .then(response => {
                 this.cart = {
                     items: [],
                     total: 0
                 }
                 

                 this.updateCartDisplay()
                 this.loadCartPage()

             })
             .catch(error => {

                 const data = {
                     error: "Empty cart failed."
                 };

                 templateBuilder.append("error", data, "errors")
             })
    }

    updateCartDisplay()
    {
        try {
            const itemCount = this.cart.items.length;
            const cartControl = document.getElementById("cart-items")

            cartControl.innerText = itemCount;
        }
        catch (e) {

        }
    }
}





document.addEventListener('DOMContentLoaded', () => {
    cartService = new ShoppingCartService();

    if(userService.isLoggedIn())
    {
        cartService.loadCart();
    }

});
