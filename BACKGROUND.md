# Customer Care Dashboard for Payment Gateway Transactions

## Challenges

1. Monitoring transactions
2. Resolving payment issues
3. Handling disputes
4. Ensuring smooth user experience

## Objective

Design and develop a Customer Care Dashboard that provides:
1. Centralized view of payment gateway transactions
2. Help support teams efficiently
	- track
	- analyze
	- resolve customer issues

## Key Objectives

1. Provide real-time visibility into transaction statuses
2. Enable customer care agents to quickly investigate and resolve payment-related issues
3. Offer actionable insights through analytics and reporting
4. Ensure secure access and auditability of all customer care actions


## Functional Requirements
### Transaction Monitoring
1. Real-time feed of transactions (Successful, Pending, Failed)
2. Search transactions using:
	- Transaction ID
	- Customer email or ID
3. Filters based on:
	- Date range
	- Merchant
	- Payment method
	- Customer Support Capabilities
4. Ability to view detailed transaction information
5. Track refunds and chargebacks
6. Maintain audit logs for all customer care actions

### Analytics & Reporting
7. Dashboards with graphs showing:
	- Transaction success vs failure rates
	- Failure trends
	- Refund and chargeback ratios
8. Downloadable reports:
	- Daily
	- Weekly
	- Monthly

### Security & Access Control
9. Role-based access control:
	- Customer Care Agent
	- Supervisor
	- Admin
10. Secure authentication and authorization


## Technical Requirements (Mandatory)
### Backend

- Java (latest stable version)
- Spring Boot (latest stable release)
- Spring Reactive (WebFlux) – preferred
- Database: MongoDB or MySQL / PostgreSQL
- API Gateway using Spring Cloud Gateway
- Security:
	- OAuth 2.0 authentication
	- SSL-enabled communication

### Frontend (Optional)
- React or Angular for UI
- Clean, user-friendly dashboard views

## Evaluation Criteria
- Code structure and design
- API design and scalability
- Security implementation
- Handling of real-time data
- Clarity of documentation
- Overall problem-solving approach