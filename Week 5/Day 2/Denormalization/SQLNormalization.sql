CREATE DEFINER=`cecs323sus19`@`10.%` TRIGGER `department_AFTER_UPDATE` AFTER UPDATE ON `department` FOR EACH ROW BEGIN
	UPDATE course
	SET chair = new.chair
	WHERE departmentName = old.deptName;
END
CREATE DEFINER=`cecs323sus19`@`10.%` TRIGGER `course_BEFORE_INSERT` BEFORE INSERT ON `course` FOR EACH ROW BEGIN
    SET new.chair = 
    (SELECT department.chair 
    from department where new.departmentName = department.deptname);
END
CREATE DEFINER=`cecs323sus19`@`10.%` TRIGGER `course_BEFORE_UPDATE` BEFORE UPDATE ON `course` FOR EACH ROW BEGIN
	if new.chair != old.chair then 
		SET new.chair = 
		(SELECT department.chair 
		from department where new.departmentName = department.deptname);
    end if;
END
CREATE DEFINER=`cecs323sus19`@`10.%` TRIGGER `deptcourse_BEFORE_INSERT` BEFORE INSERT ON `deptcourse` FOR EACH ROW BEGIN
	if (
		SELECT count(*) FROM department
		WHERE new.college = department.college AND new.officeBldg = department.officeBldg AND new.officeNo = department.officeNo AND new.chair = department.chair
		) = 0 then
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "NEW ROW DOES NOT MATCH DEPARTMENT INFO";
	END IF;
END
CREATE DEFINER=`cecs323sus19`@`10.%` TRIGGER `deptcourse_BEFORE_UPDATE` BEFORE UPDATE ON `deptcourse` FOR EACH ROW BEGIN
	if (new.college != old.college or new.deptname != old.deptname or new.chair != old.chair 
		or new.officeBldg != old.officeBldg or new.officeNo != old.officeNo) then 
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Will cause department info to misalign";
    end if;
END