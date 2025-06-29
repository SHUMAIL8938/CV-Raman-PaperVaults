# **CV Raman PaperVaults** 📚

**CV Raman PaperVaults** is a sleek web portal for CV Raman Global University professors to manage research publications effortlessly. Inspired by C.V. Raman, it replaces manual tracking with a centralized, intuitive system. 🚀

## **About the Project** 🌟

A Java-based portal to streamline publication management for journals, conferences, book chapters, and projects, ensuring easy access for faculty.

## **Why This Project?** 🤔

Manual tracking is inefficient and error-prone. Our solution provides:
- Centralized data storage 📊
- Real-time access ⏰
- Categorized publications 📈

## **Features** ✨

- **User Management**: Secure signup/login 🔒
- **Publication Management**:
  - Add: Input title, type, date, abstract, files 📝
  - View: Filterable table with details 📋
  - Edit: Update securely ✏️
  - Delete: Remove with confirmation 🗑️
- **GUI**: Clean HTML/CSS interface 😊
- **Dashboard**: Shows counts and records 📊

## **Technologies** 🛠️

- **Java**: Core language [Docs](https://docs.oracle.com/en/java/javase/17/docs/api/)
- **JDBC**: MySQL connector [Docs](https://docs.oracle.com/en/java/javase/17/docs/api/java.sql/module-summary.html)
- **MySQL**: Database [Docs](https://dev.mysql.com/doc/)
- **HTML/CSS**: GUI [Docs](https://developer.mozilla.org/en-US/docs/Web/HTML)
- **IDE**: NetBeans/Eclipse [Docs](https://www.eclipse.org/documentation/)

## **Database** 📋

| Field      | Type          | Description          |
|------------|---------------|----------------------|
| `id`       | INT (PK)      | Auto-incremented ID  |
| `prof_id`  | INT (FK)      | Links to `professors`|
| `type`     | VARCHAR(20)   | Journal, etc.        |
| `title`    | VARCHAR(200)  | Publication title    |
| `abstract` | TEXT          | Summary              |

## **Screenshots** 🖼️

- **Signup/Login**: Registration and login forms
- **Dashboard**: Counts and user publications
- **All Publications**: Table with filters

## **Benefits** 🌈

- Reduces tracking errors ⏳
- Organizes data 📂
- Scalable design 🚀

## **Future Plans** 🔮

- Password hashing 🔐
- PDF/Excel export 📄
- Cloud database ☁️
