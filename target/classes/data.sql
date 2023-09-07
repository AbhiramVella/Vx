/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  unify
 * Created: 06-Sept-2023
 */

-- INSERT INTO users (firstName, lastName, age, email, mobileNumber, address, password,userType, status)
-- VALUES ('vx', 'admin', 18, 'admin@vx.com', 9999999999, 'Hyderabad','admin@369', 2, 0)
-- WHERE NOT EXISTS(SELECT * FROM users WHERE email='admin@vx.com');


-- If Not Exists(select * from users where email='admin@vx.com')
-- Begin
-- insert into users (firstName, lastName, age, email, mobileNumber, address, password,userType, status) values ('vx', 'admin', '18', 'admin@vx.com', '9999999999', 'Hyderabad','admin@369', '2', '0')
-- End

INSERT INTO users (first_name,last_name, age, email, mobile_number, address, password,user_type, status)
SELECT * FROM (SELECT 'Vx' AS first_name, 'Admin' AS last_name, '18' AS age, 'admin@vx.com' AS email, '9999999999' AS mobile_number, 'Hyderabad' AS address,'$2y$10$eHpzd1Whcw0E8Cn31xF8Deg4UvscciFUhyDjkgiqEnkVPjho4UV4C'  AS password, '2' AS user_type, '0' AS status) AS temp
WHERE NOT EXISTS (
    SELECT email FROM users WHERE email = 'admin@vx.com'
) LIMIT 1;


INSERT INTO vaccination (name, code)
SELECT name, code
FROM
(
  SELECT name , code
  FROM
  (
     SELECT 'Chickenpox' as name , 'Chickenpox' as code 
  ) AS temp_1
  WHERE NOT EXISTS
  (
     SELECT name FROM vaccination WHERE name = 'Chickenpox'
  )
  UNION ALL
  SELECT name , code
  FROM
  (
     SELECT 'Dengue' as name , 'Dengue' as code 
  ) AS temp_2
  WHERE NOT EXISTS
  (
     SELECT name FROM vaccination WHERE name = 'Dengue'
  )
  UNION ALL
  SELECT name , code
  FROM
  (
     SELECT 'Diphtheria' as name , 'Diphtheria' as code 
  ) AS temp_3
  WHERE NOT EXISTS
  (
     SELECT name FROM vaccination WHERE name = 'Diphtheria'
  )
  UNION ALL

  SELECT name , code
  FROM
  (
     SELECT 'Covid' as name , 'Covid' as code 
  ) AS temp_4
  WHERE NOT EXISTS
  (
     SELECT name FROM vaccination WHERE name = 'Covid'
  )
  UNION ALL

  SELECT name , code
  FROM
  (
     SELECT 'Flu' as name , 'Flu' as code 
  ) AS temp_5
  WHERE NOT EXISTS
  (
     SELECT name FROM vaccination WHERE name = 'Flu'
  )
  UNION ALL


  SELECT name , code
  FROM
  (
     SELECT 'Hepatitis A' as name , 'Hepatitis A' as code 
  ) AS temp_6
  WHERE NOT EXISTS
  (
     SELECT name FROM vaccination WHERE name = 'Hepatitis A'
  )
  UNION ALL


  SELECT name , code
  FROM
  (
     SELECT 'Hepatitis B' as name , 'Hepatitis B' as code 
  ) AS temp_7
  WHERE NOT EXISTS
  (
     SELECT name FROM vaccination WHERE name = 'Hepatitis B'
  )
  UNION ALL


  SELECT name , code
  FROM
  (
     SELECT 'Hib' as name , 'Hib' as code 
  ) AS temp_8
  WHERE NOT EXISTS
  (
     SELECT name FROM vaccination WHERE name = 'Hib'
  )
  UNION ALL

  SELECT name , code
  FROM
  (
     SELECT 'HPV' as name , 'HPV' as code 
  ) AS temp_9
  WHERE NOT EXISTS
  (
     SELECT name FROM vaccination WHERE name = 'HPV'
  )
  UNION ALL

  SELECT name , code
  FROM
  (
     SELECT 'Measles' as name , 'Measles' as code 
  ) AS temp_10
  WHERE NOT EXISTS
  (
     SELECT name FROM vaccination WHERE name = 'Measles'
  )
) alias_vaccination;