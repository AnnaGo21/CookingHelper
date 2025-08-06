# Cooking Helper API

A comprehensive Spring Boot REST API for managing food items, recipes, and nutritional information with user authentication and authorization.

## 🚀 Features

- **User Management**: Registration, authentication with JWT tokens
- **Food Database**: Comprehensive food items with nutritional information
- **Recipe Management**: Create and manage cooking recipes
- **Nutritional Search**: Search foods by calories, protein, fat, carbohydrates
- **Ingredient Tracking**: Manage recipe ingredients and relationships
- **Security**: JWT-based authentication and role-based authorization
- **API Documentation**: Interactive Swagger/OpenAPI documentation

## 🛠 Tech Stack

- **Backend**: Spring Boot 3.1.0, Java 17
- **Database**: MySQL 8.0 with Flyway migrations
- **Security**: Spring Security with JWT authentication
- **Documentation**: SpringDoc OpenAPI 3
- **Build Tool**: Maven
- **Containerization**: Docker & Docker Compose
- **Testing**: JUnit, Spring Boot Test

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker & Docker Compose
- Git

## 🚀 Quick Start

### Using Docker (Recommended)

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd cooking-helper
   ```

2. **Build and run with Docker Compose**
   ```bash
   docker-compose up --build
   ```

3. **Access the application**
   - API Base URL: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`

### Manual Setup

1. **Clone and build**
   ```bash
   git clone <repository-url>
   cd cooking-helper
   mvn clean install
   ```

2. **Setup MySQL Database**
   ```sql
   CREATE DATABASE cooking_helper;
   ```

3. **Configure application.properties**
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/cooking_helper
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

## 🗄 Database Schema

The application uses Flyway for database migrations. Key entities include:

- **Users**: User accounts with roles
- **Foods**: Food items with nutritional data
- **Recipes**: Cooking recipes
- **Ingredients**: Recipe ingredients
- **Ingredients_Recipes**: Many-to-many relationship table

## 🔐 Authentication

### Register a new user
```bash
POST /api/users/register
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "securePassword123"
}
```

### Login
```bash
POST /api/users/sign-in
Content-Type: application/json

{
  "username": "john_doe",
  "password": "securePassword123"
}
```

Response includes JWT token for authenticated requests.

## 📚 API Endpoints

### User Management
- `POST /api/users/register` - Register new user
- `POST /api/users/sign-in` - User authentication
- `GET /api/users/{id}` - Get user by ID

### Food Management
- `GET /api/food` - Get all foods
- `PUT /api/food/create` - Create new food item
- `DELETE /api/food/delete/{foodId}` - Delete food item
- `GET /api/food/name/{foodName}` - Search food by name

### Nutritional Search
- `GET /api/food/min-calories/{minCalories}` - Foods with minimum calories
- `GET /api/food/min-protein/{minProtein}` - Foods with minimum protein
- `GET /api/food/max-fat/{maxFat}` - Foods with maximum fat
- `GET /api/food/min-carbos/{minCarbohydrates}` - Foods with minimum carbs

### Advanced Search
- `POST /api/food/protein-range` - Search by protein range and type
- `POST /api/food/fat-range` - Search by fat range and type
- `POST /api/food/carbo-range` - Search by carbohydrate range and type
- `POST /api/food/calories-range` - Search by calorie range and type

### Recipe Management
- `GET /api/recipes` - Get all recipes
- `POST /api/recipes` - Create new recipe
- `GET /api/recipes/{id}` - Get recipe by ID
- `PUT /api/recipes/{id}` - Update recipe
- `DELETE /api/recipes/{id}` - Delete recipe

## 🔧 Configuration

### Environment Variables
```bash
# Database Configuration
SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/cooking_helper
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=root

# JWT Configuration
JWT_SECRET=your-secret-key
JWT_EXPIRATION=86400000
```

### Docker Compose Services
- **app**: Spring Boot application (port 8080)
- **db**: MySQL 8.0 database (port 3307)

## 🧪 Testing

Run the test suite:
```bash
mvn test
```

Test categories:
- Unit tests for services and repositories
- Integration tests for controllers
- Database repository tests

## 📖 API Documentation

Interactive API documentation is available at:
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

## 🏗 Project Structure

```
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── config/          # Security & JWT configuration
│   │   ├── controller/      # REST controllers
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── entity/         # JPA entities
│   │   ├── repository/     # Data repositories
│   │   ├── service/        # Business logic
│   │   ├── security/       # Security components
│   │   └── util/           # Utility classes
│   └── resources/
│       ├── db/migration/   # Flyway database migrations
│       └── application.properties
└── test/                   # Test classes
```

## 🚀 Deployment

### Production Deployment
1. Build the application: `mvn clean package`
2. Use the generated JAR: `target/demo-0.0.1-SNAPSHOT.jar`
3. Set production environment variables
4. Deploy with your preferred method (Docker, Kubernetes, etc.)

### Docker Production
```bash
docker build -t cooking-helper:latest .
docker run -p 8080:8080 cooking-helper:latest
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/new-feature`
3. Commit changes: `git commit -am 'Add new feature'`
4. Push to branch: `git push origin feature/new-feature`
5. Submit a Pull Request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🐛 Troubleshooting

### Common Issues

**Database Connection Error**
- Ensure MySQL is running
- Check database credentials in application.properties
- Verify database exists

**JWT Token Issues**
- Check JWT secret configuration
- Verify token expiration settings
- Ensure proper Authorization header format: `Bearer <token>`

**Docker Issues**
- Run `docker-compose down` before rebuilding
- Check port conflicts (8080, 3307)
- Verify Docker daemon is running

## 📞 Support

For support and questions:
- Create an issue in the repository
- Check existing documentation
- Review API documentation at `/swagger-ui.html`

---

**Built with ❤️ using Spring Boot**