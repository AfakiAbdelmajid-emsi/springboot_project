# Gestion System - Frontend Application

A Next.js frontend application for managing products, stock, commercial operations, and orders. This application connects to three backend microservices.

## 📋 Prerequisites

Before running this application, make sure you have:

- **Node.js** (v18 or higher) and **npm** installed
- The three backend services running:
  - **Gestion Commercial** (Port 8081)
  - **Gestion Stock** (Port 8082)
  - **Gestion Vente** (Port 8080)

## 🚀 Installation

1. **Clone or navigate to the project directory:**
   ```bash
   cd my-app
   ```

2. **Install dependencies:**
   ```bash
   npm install
   ```

## ▶️ Running the Application

### Step 1: Start Backend Services

Make sure all three backend services are running:

1. **Gestion Commercial** (Port 8081)
   ```bash
   # Navigate to: C:\Users\PC\Desktop\sts-4.31.0.RELEASE\doc\gestion_commercial2
   # Run: mvn spring-boot:run
   # Or use your IDE to run GestionCommercialApplication
   ```

2. **Gestion Stock** (Port 8082)
   ```bash
   # Navigate to: C:\Users\PC\Desktop\sts-4.31.0.RELEASE\doc\gestion_stock
   # Run: mvn spring-boot:run
   # Or use your IDE to run GestionStockApplication
   ```

3. **Gestion Vente** (Port 8080)
   ```bash
   # Navigate to: C:\Users\PC\Desktop\sts-4.31.0.RELEASE\doc\gestion_vente
   # Run: mvn spring-boot:run
   # Or use your IDE to run GestionVenteApplication
   ```

### Step 2: Start Frontend Development Server

```bash
npm run dev
```

The application will be available at [http://localhost:3000](http://localhost:3000)

## 📱 Available Pages

### Authentication
- **`/login`** - User login page
- **`/register`** - User registration page

### Main Features
- **`/products`** - View all products with prices and stock quantities
- **`/stock`** - Manage stock (view, add, subtract quantities)
- **`/commercial`** - Manage products and commands (add products, add commands)
- **`/orders`** - Create orders and download invoices

## 🔌 API Endpoints

All API calls are centralized in `src/lib/api.ts`. The application connects to:

### Service 1: Gestion Commercial (Port 8081)
- `GET /api/commercial/produits` - Get all products with prices
- `POST /api/commercial/produits/add` - Add a new product
- `POST /api/commercial/add` - Add a command/order

### Service 2: Gestion Stock (Port 8082)
- `GET /api/stock` - Get all stock items
- `POST /api/stock/add` - Add stock item
- `PUT /api/stock/subtract` - Subtract quantity from stock

### Service 3: Gestion Vente (Port 8080)
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration
- `GET /api/vente/produits` - Get products with combined info
- `POST /api/vente/commande` - Create a new order
- `GET /api/vente/facture/{id}` - Download invoice PDF

## 🎯 Usage Flow

1. **Register/Login**: Start by registering a new user or logging in with existing credentials
2. **View Products**: Navigate to Products page to see all available products
3. **Manage Stock**: Go to Stock page to add or subtract stock quantities
4. **Manage Commercial**: Use Commercial page to add products or commands
5. **Create Orders**: Use Orders page to create new orders and download invoices

## 🛠️ Project Structure

```
src/
├── app/                    # Next.js app router pages
│   ├── login/             # Login page
│   ├── register/         # Registration page
│   ├── products/         # Products view page
│   ├── stock/            # Stock management page
│   ├── commercial/       # Commercial management page
│   ├── orders/           # Orders management page
│   └── layout.tsx        # Root layout
├── components/           # React components
│   └── Navigation.tsx    # Navigation bar component
└── lib/                 # Utility files
    ├── api.ts           # All API functions
    └── types.ts         # TypeScript interfaces
```

## 🔐 Authentication

- JWT tokens are stored in `localStorage`
- Protected routes automatically redirect to login if not authenticated
- Token is included in API request headers automatically

## 🎨 Features

- ✅ User authentication (login/register)
- ✅ Product catalog with prices and stock
- ✅ Stock management (add/subtract)
- ✅ Commercial product management
- ✅ Order creation
- ✅ Invoice PDF download
- ✅ Responsive design with dark mode support
- ✅ Error handling and success messages
- ✅ Form validation

## 🐛 Troubleshooting

### Backend Services Not Running
If you see connection errors:
- Verify all three backend services are running on ports 8080, 8081, and 8082
- Check if the services are accessible at their respective URLs

### CORS Errors
- All backend services have `@CrossOrigin("*")` enabled, so CORS should work
- If issues persist, check backend service configurations

### Authentication Issues
- Clear localStorage if token is corrupted: `localStorage.clear()`
- Register a new user if login fails

### Port Already in Use
If port 3000 is already in use:
```bash
# Kill the process using port 3000 (Windows)
netstat -ano | findstr :3000
taskkill /PID <PID> /F

# Or use a different port
npm run dev -- -p 3001
```

## 📦 Build for Production

```bash
npm run build
npm start
```

## 🛠️ Technologies Used

- **Next.js 16** - React framework
- **TypeScript** - Type safety
- **Tailwind CSS** - Styling
- **React 19** - UI library

## 📝 Notes

- The application automatically redirects to `/login` if not authenticated
- All API calls include authentication tokens when available
- Date formats should be in `YYYY-MM-DD` format
- Stock quantities must be positive integers

---

**Happy Coding! 🚀**
