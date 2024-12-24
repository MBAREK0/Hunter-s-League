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

# --- 2024-11-02 10:00:00 - Add score to participation
WITH ParticipationScores AS (
    SELECT
        p.id AS participation_id,
        SUM(
                s.points +
                (h.weight *
                 CASE
                     WHEN s.category = 'BIG_GAME' THEN 3
                     WHEN s.category = 'SEA' THEN 9
                     WHEN s.category = 'BIRD' THEN 5
                     ELSE 1
                     END) *
                CASE
                    WHEN s.difficulty = 'COMMON' THEN 1
                    WHEN s.difficulty = 'RARE' THEN 2
                    WHEN s.difficulty = 'EPIC' THEN 3
                    WHEN s.difficulty = 'LEGENDARY' THEN 5
                    ELSE 1
                    END
        ) AS calculated_score
    FROM participation p
             JOIN hunt h ON p.id = h.participation_id
             JOIN species s ON h.species_id = s.id
    GROUP BY p.id
)
UPDATE participation
SET score = ParticipationScores.calculated_score
    FROM ParticipationScores
WHERE participation.id = ParticipationScores.participation_id;