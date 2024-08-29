USE expenses;

DELIMITER $$

CREATE PROCEDURE InsertRandomExpenseCategories()
BEGIN
    -- Declare variables
    DECLARE expense_id INT;
    DECLARE category_id INT;
    DECLARE num_categories INT;
    DECLARE random_index INT;
    DECLARE num_existing_categories INT;
    DECLARE done INT DEFAULT 0;

    -- Declare a cursor for selecting all distinct expense_ids from the expenses table
    DECLARE expense_cursor CURSOR FOR
        SELECT id FROM expenses;

    -- Declare continue handler for cursor to manage end of data
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    -- Temporary storage for category IDs
    CREATE TEMPORARY TABLE temp_existing_categories AS
    SELECT id FROM categories;

    -- Get the number of existing categories
    SET num_existing_categories = (SELECT COUNT(*) FROM temp_existing_categories);

    -- Open the cursor
    OPEN expense_cursor;

    -- Loop through each expense_id
    expense_loop:
    LOOP
        FETCH expense_cursor INTO expense_id;

        -- Exit loop if there are no more expense_ids
        IF done THEN
            LEAVE expense_loop;
        END IF;

        -- Generate a random number of categories to assign (between 1 and the number of existing categories)
        SET num_categories = FLOOR(1 + RAND() * num_existing_categories);

        -- Create a temporary table to store assigned categories to avoid duplicates
        CREATE TEMPORARY TABLE temp_assigned_categories
        (
            category_id INT PRIMARY KEY
        );

        WHILE (SELECT COUNT(*) FROM temp_assigned_categories) < num_categories
            DO
                -- Generate a random index to select a category_id from temp_existing_categories
                SET random_index = FLOOR(RAND() * num_existing_categories);

                -- Ensure random_index is within valid range
                IF random_index >= 0 AND random_index < num_existing_categories THEN
                    -- Get the category_id at the random_index
                    SET category_id = (SELECT id FROM temp_existing_categories LIMIT random_index, 1);

                    -- Insert into the temp table only if it's not already there
                    IF (SELECT COUNT(*) FROM temp_assigned_categories WHERE category_id = category_id) = 0 THEN
                        INSERT INTO temp_assigned_categories (category_id) VALUES (category_id);

                        -- Insert the randomly selected category_id for the current expense_id
                        INSERT INTO expenses_categories (expense_id, category_id) VALUES (expense_id, category_id);
                    END IF;
                END IF;

            END WHILE;

        -- Drop the temporary table to reset for the next expense_id
        DROP TEMPORARY TABLE IF EXISTS temp_assigned_categories;

    END LOOP;

    -- Close the cursor
    CLOSE expense_cursor;

    -- Clean up
    DROP TEMPORARY TABLE IF EXISTS temp_existing_categories;
END$$

DELIMITER ;

CALL InsertRandomExpenseCategories();

