-- Seeded admin users for Task 1.2

-- Seeded two users for testing:
--   hr_user, hrpass    (ROLE_HR)
--   manager_user, managerpass  (ROLE_MANAGER)

INSERT INTO admin_user (username, password, role)
SELECT 'hr_user', '$2a$10$Smj6pDOgvuD5SVJtYdmrVeDxtGVqcJLcdxWAsvmFWc8qNv5lkfjwC', 'HR'
    WHERE NOT EXISTS (SELECT 1 FROM admin_user WHERE username = 'hr_user');

INSERT INTO admin_user (username, password, role)
SELECT 'manager_user', '$2a$10$nSzxAy8BJyRM6/cFCCUhIuCX4kDNmQWpOhvWDlwZF7jfYn/VpxELy', 'MANAGER'
    WHERE NOT EXISTS (SELECT 1 FROM admin_user WHERE username = 'manager_user');