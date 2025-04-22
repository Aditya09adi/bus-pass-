-- Insert sample users
INSERT INTO users (user_id, name, email, phone, address, registration_date) VALUES 
('USR123456', 'John Doe', 'john.doe@example.com', '1234567890', '123 Main St, City', '2023-01-15'),
('USR789012', 'Jane Smith', 'jane.smith@example.com', '9876543210', '456 Oak Ave, Town', '2023-02-20'),
('USR345678', 'Bob Johnson', 'bob.johnson@example.com', '5551234567', '789 Pine Rd, Village', '2023-03-10');

-- Insert sample routes
INSERT INTO routes (route_id, route_name, start_point, end_point, zone, distance, status) VALUES 
('R1001', 'Downtown Express', 'Central Station', 'Downtown Mall', 'zone 1', 5.2, 'Active'),
('R1002', 'Northside Loop', 'North Station', 'University Campus', 'zone 2', 8.7, 'Active'),
('R1003', 'East-West Connector', 'East Terminal', 'West Plaza', 'zone 3', 12.5, 'Active');

-- Insert sample bus passes
INSERT INTO bus_passes (pass_id, user_id, pass_type, start_date, end_date, route_access, status, price) VALUES 
('BP1001', 1, 'monthly', '2023-06-01', '2023-06-30', 'all routes', 'Active', 80.00),
('BP1002', 2, 'student', '2023-06-01', '2023-08-31', 'zone 1', 'Active', 50.00),
('BP1003', 3, 'annual', '2023-01-01', '2023-12-31', 'all routes', 'Active', 800.00);