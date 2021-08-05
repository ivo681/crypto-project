#CryptoMania Bulgaria

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) for the front-end
and [Spring](https://spring.io/) for the back-end

#CryptoMania Bulgaria Project

- [Description](#description)
- [Starting the Full Application](#starting-the-full-application)
- [Authorities and Access](#authorities-and-access)
    - [Anonymous](#anonymous)
    - [User](#user)
    - [Admin](#admin)
- [Authentication](#authentication)
- [Not Logged Pages](#not-logged-pages)
  - [Home](#home)
  - [About-Us](#about-us)
  - [Products](#products)
- [Logged In Pages](#logged-in-pages)
  - [Marketplace](#marketplace)
  - [My Wallet](#my-wallet)
- [Backend Information](#backend-information)
- [Frontend Information](#frontend-information)
- [Sample Data for Testing](#sample-data-for-testing)

## Description
CryptoMania Bulgaria EOOD is a fictional cryptocurrency exchange platform. 
Many of my peers and close friends
have decided to indulge in crypto exchanges and around them, I have gathered a lot of information about current exchange platforms.
The digital currencies that are on offer in this project are 100% fictional and inspired from local Bulgarian events and personas. 
The purpose is to bring users a variety of exciting currencies for which the prices fluctuate hourly.

## Starting the Full Application
For an IDE, the project was developed on [IntelliJ Ultimate](https://www.jetbrains.com/help/idea/2021.2/discover-intellij-idea.html).
It is recommended to test it on the same IDE for satisfactory experiences. 
In order to start it please download the complete project from the repository. After the indexation you will be able to
boot the application. To do this you need to start both the Spring Backend Application and the Angular CLI frontend app.
On the first start the backend will bring up the MySQL database and populate it with Json data (bank accounts(fictional)
and crypto coins).

## Authorities and Access

#### Anonymous
Anonymous users have access to front page, About Us page, Products page and User Login and Registration pages
of the exchange platform

For the authenticated users, the app provides 2 user roles:
USER AND ADMIN

#### User
Once logged in, Users have access to the home page where they are greeted and have options to choose 3 pages:
Marketplace, My Wallet and Transactions. Users are able to buy crypto coins, sell the ones they have in their virtual Wallet
and view their transactions with details.

#### Admin
The first user that register on the website receives an additional role/authority- Admin. Apart from the functionalities which all
users have, the admin can access the Admin Panel, while others are blocked due to being unauthorized.

## Authentication
Anonymous users can navigate to the login and register pages using the buttons on the right side of the header.
Both pages use template-driven forms with validations, guiding the users what data is expected to be provided(ex:
email, date of birth, matching passwords, following a password pattern, etc). 
After the forms are submitted the backend server makes additional validations
such as whether the provided email is already used, the user being over 18 years old and others. On both ends the user
is shown appropriate messages for guidance. Once registered and logged in,
on the header of the page there is a button for Log Out.

# Not Logged pages
## Home Page
The Home Page displays a couple of coins with the website logo, short info and button for the About Us page.

## About Us Page
The About Us page gives some information how the exchange platform project was developed and some backstory.

## Products Page
The Products page gives static information in the form of images what coins are on the offer in the platform.

# Logged In Pages

## Marketplace

On the Marketplace page a table loads with a list of available cryptocurrencies for purchase. With a GET Request
the currencies are fetched from the Spring backend and on the front-end the information is stylised 
(ex: coins that are dropping in price have class text-danger while those on the rise have class text-success).

When a user clicks on a "Buy Now" button he is directed to page with detailed information 
in regards to the crypto coin- gif Image, initial price, price from yesterday, price from last hour with stylised
change percentages how the prices move in regards to the current one. On this page, using a form a user can choose
how many coins he wishes to order with the total price updating right away.

When you click again "Buy now" the user is directed to a checkout page with the order information on the left side
and the credit/debit card form on the right. As mentioned on the page, please do not use your real life bank details,
all of this is still fictional. The template-driven form has validations such as exactly 16 digits for the card number,
expiration date format and 3 digit cvv. In order to make purchases please use the sample bank accounts provided at the bottom of the
README file. If the input is invalid or if the provided bank account is invalid/has low balance then the purchase isn't completed
and the system keeps a declined transaction on the order's history. The user is informed via a message on the page.
If the transaction is successful then the bank account has the
order amount subtracted and the user is directed to a confirmation page.

## My Wallet

On the My Wallet page a table loads with a list of the purchased cryptocurrencies a user can sell. 
With a GET Request the currencies are fetched from the Spring backend and on the front-end the information is stylised
(ex: coins that are dropping in price have class text-danger while those on the rise have class text-success).

When a user clicks on a "Sell Now" button he is directed to a checkout page with the order information on the left side
and the credit/debit card form on the right. As mentioned on the page, please do not use your real life bank details. 
The template-driven form has validations such as exactly 16 digits for the card number,
expiration date format and 3 digit cvv. In order to make purchases please use the sample bank accounts provided at the bottom of the
README file. If the input is invalid, if the provided bank account is invalid or if the project's bank account 
has low balance then the purchase isn't completed and the system keeps a declined transaction on the order's history.
The user is informed via a message on the page. If the transaction is successful then the bank account has the
order amount subtracted and the user is directed to a confirmation page.

## Transactions

On the Transactions page a table loads with a list of the attempted user transactions on the platform.
The user can see the date & time, coin name, order number, order type, price and status.
On the right side is a funny picture showing how many people think crypto trading works.

## Admin Panel

On the Admin Panel page a table is loaded with a full list of the attempted transactions on the platform for the past
24 hours. The admin can see exactly what the users are doing and the status of operations.

# Backend information
The Backend has an implemented Spring Security system with a custom configuration on the cross requests made from the
Angular CLI server. The structure is as if follows: config(security, bean, Jwt token, etc),
events(cron and scheduled when to update coin prices, bank account balances and clear incomplete orders)
, exceptions, init(populating the database tables with prepared information),
model(binding, dtos, enums, service, validators), repository, service, web controllers.

# Frontend information
The Frontend uses two guards- authentication and admin in order to implement a level of security.
The structure is as it follows: about, core(header, footer, guards), home, modules(admin, errors, market, product, shared, transaction, user, wallet).

# Sample Data for Testing
Valid bank account for testing with high balance for purchases
"number": 1111111111111111,
"full_name": "Tester One",
"validTo": "2023-02-24",
"cvv": 101,
"balance": 20000

Valid bank account for testing with low balance for purchases
"number": 2222222222222222,
"full_name": "Tester Two",
"validTo": "2023-02-24",
"cvv": 102,
"balance": 0.10

You can change some details to check if the backend catches 
the invalid account input.
