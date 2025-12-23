🎮 GameStop Retail Interface
A robust, Java-based retail management system developed by a team of six. This application serves as a comprehensive interface for managing a high-volume database of video games, customer accounts, and employee logistics using custom-implemented data structures.

🛠️ Technical Core
Data Structures: Custom implementation of Binary Search Trees (BST) and Hash Tables.Performance: Optimized for $O(\log n)$ search/sorting for inventory and $O(1)$ constant-time lookup for user authentication.Persistence: Built-in File I/O layer that ensures all database changes (inventory updates, new orders, user accounts) are saved to local text files upon exit.Security: Role-based access control using Object-Oriented Programming (OOP) principles to separate Manager, Employee, and Customer functionalities.

🚀 Key Features
1. Advanced Inventory Search (BST)To handle a large catalog efficiently, the system utilizes two separate Binary Search Trees:
   Title Search: Uses a custom TitleComparator for alphabetical indexing.
   Genre Filtering: Allows users to view games by category using a GenreComparator.
2. Scalable User Management (Hash Table)Customer and Employee data is managed via a Hash Table, ensuring that login verification and account retrieval remain near-instantaneous as the user base grows.
3. Order Processing & Logistics
   Priority Queue Logic: Managers can view and ship orders based on priority (Standard, Rush, or Overnight shipping).
   Data Synchronization: Uses a robust writeDataToFile method to maintain data integrity across sessions.
