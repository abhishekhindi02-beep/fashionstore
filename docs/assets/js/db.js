const MOCK_CATEGORIES = [
    { id: 1, name: "Men" },
    { id: 2, name: "Women" },
    { id: 3, name: "Accessories" }
];

const MOCK_PRODUCTS = [
    {
        productId: 1,
        productName: "Classic Running Shoes",
        price: 2499.00,
        imageUrl: "assets/images/shoes.jpg.png",
        description: "Premium lightweight running shoes designed for ultimate speed and extreme comfort. Features breathable mesh, shock-absorbing midsole, and flexible traction outsole.",
        categoryId: 1,
        isActive: true,
        sizes: [
            { sizeLabel: "7", stockQuantity: 15 },
            { sizeLabel: "8", stockQuantity: 20 },
            { sizeLabel: "9", stockQuantity: 12 },
            { sizeLabel: "10", stockQuantity: 8 }
        ]
    },
    {
        productId: 2,
        productName: "Classic Cotton T-Shirt",
        price: 799.00,
        imageUrl: "assets/images/tshirts.jpg.png",
        description: "An everyday essential. 100% combed organic cotton t-shirt with a modern regular fit, durable ribbed crew neck, and ultra-soft feel against the skin.",
        categoryId: 1,
        isActive: true,
        sizes: [
            { sizeLabel: "S", stockQuantity: 30 },
            { sizeLabel: "M", stockQuantity: 45 },
            { sizeLabel: "L", stockQuantity: 40 },
            { sizeLabel: "XL", stockQuantity: 25 }
        ]
    },
    {
        productId: 3,
        productName: "Luxury Chronograph Watch",
        price: 5999.00,
        imageUrl: "assets/images/watch.jpg.png",
        description: "Sophisticated styling meets precision timekeeping. Featuring a premium leather strap, durable stainless steel casing, elegant dark watch face, and water-resistance up to 50 meters.",
        categoryId: 3,
        isActive: true,
        sizes: [
            { sizeLabel: "Regular", stockQuantity: 15 }
        ]
    },
    {
        productId: 4,
        productName: "Classic Women's Jeans",
        price: 1899.00,
        imageUrl: "assets/images/women-jeans.jpg.png",
        description: "Flattering high-rise slim-fit jeans crafted from premium stretch denim. Perfect blend of style and ease, retaining its shape throughout the day.",
        categoryId: 2,
        isActive: true,
        sizes: [
            { sizeLabel: "28", stockQuantity: 20 },
            { sizeLabel: "30", stockQuantity: 25 },
            { sizeLabel: "32", stockQuantity: 18 },
            { sizeLabel: "34", stockQuantity: 10 }
        ]
    },
    {
        productId: 5,
        productName: "Modern Women's Top",
        price: 1199.00,
        imageUrl: "assets/images/women-top.jpg.png",
        description: "Chic, light, and airy summer top with delicate shoulder strap details, dynamic stitching, and a fluid silhouette. Ideal for casual outings and warm weather.",
        categoryId: 2,
        isActive: true,
        sizes: [
            { sizeLabel: "XS", stockQuantity: 15 },
            { sizeLabel: "S", stockQuantity: 30 },
            { sizeLabel: "M", stockQuantity: 35 },
            { sizeLabel: "L", stockQuantity: 20 }
        ]
    },
    {
        productId: 6,
        productName: "Elegant Floral Dress",
        price: 2999.00,
        imageUrl: "assets/images/womendress.jpg.png",
        description: "Stunning flowy evening dress featuring a premium floral print, adjustable waist ties, and soft inner lining. Designed to make a graceful statement.",
        categoryId: 2,
        isActive: true,
        sizes: [
            { sizeLabel: "S", stockQuantity: 10 },
            { sizeLabel: "M", stockQuantity: 18 },
            { sizeLabel: "L", stockQuantity: 15 },
            { sizeLabel: "XL", stockQuantity: 8 }
        ]
    }
];

// Initialize database
function initDatabase() {
    if (!localStorage.getItem("categories")) {
        localStorage.setItem("categories", JSON.stringify(MOCK_CATEGORIES));
    }
    if (!localStorage.getItem("products")) {
        localStorage.setItem("products", JSON.stringify(MOCK_PRODUCTS));
    }
    if (!localStorage.getItem("users")) {
        localStorage.setItem("users", JSON.stringify([]));
    }
    if (!localStorage.getItem("orders")) {
        localStorage.setItem("orders", JSON.stringify([]));
    }
}

initDatabase();

// Helper database functions
function getProducts() {
    return JSON.parse(localStorage.getItem("products"));
}

function getProductById(id) {
    const products = getProducts();
    return products.find(p => p.productId === parseInt(id));
}

function getCategories() {
    return JSON.parse(localStorage.getItem("categories"));
}
