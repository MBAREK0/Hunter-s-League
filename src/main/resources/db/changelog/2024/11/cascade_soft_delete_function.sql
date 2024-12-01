CREATE OR REPLACE FUNCTION cascade_soft_delete()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.deleted = TRUE THEN
UPDATE participations
SET deleted = TRUE
WHERE competition_id = NEW.id;

UPDATE hunts
SET deleted = TRUE
WHERE participation_id IN (
    SELECT id FROM participations WHERE competition_id = NEW.id
);
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER competition_soft_delete_trigger
    AFTER UPDATE OF deleted ON competitions
    FOR EACH ROW
    WHEN (NEW.deleted = TRUE AND OLD.deleted = FALSE)
    EXECUTE FUNCTION cascade_soft_delete();