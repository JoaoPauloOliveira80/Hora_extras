select * from jornadastrabalho;

SELECT 
    porcentagem,
    TIME_FORMAT(
        SEC_TO_TIME(SUM(
            COALESCE(
                GREATEST(
                    TIME_TO_SEC(
                        TIMEDIFF(
                            TIMEDIFF(endJornada, startJornada),
                            CASE
                                WHEN porcentagem = 70 THEN IFNULL(TIMEDIFF(endAlmoco, startAlmoco), '00:00:00')
                                ELSE '00:00:00'
                            END
                        )
                    ) - CASE WHEN porcentagem = 70 THEN TIME_TO_SEC('08:48:00') ELSE 0 END,
                    0
                ),
                0
            )
        )),
        '%H horas e %i minutos'
    ) AS totalHorasExtrasFormatado
FROM 
    JornadasTrabalho
WHERE
    startJornada BETWEEN '2023-11-26' AND '2023-12-25'
    AND (porcentagem = 70 OR porcentagem = 110)
GROUP BY porcentagem;

SELECT 
    porcentagem,
    SEC_TO_TIME(
        SUM(
            CASE 
                WHEN porcentagem = 70 THEN 
                    GREATEST(
                        TIME_TO_SEC(
                            TIMEDIFF(
                                TIMEDIFF(endJornada, startJornada),
                                IFNULL(TIMEDIFF(endAlmoco, startAlmoco), '00:00:00')
                            )
                        ) - TIME_TO_SEC('08:48:00'),
                        0
                    )
                ELSE
                    TIME_TO_SEC(
                        TIMEDIFF(endJornada, startJornada)
                    )
            END
        )
    ) AS totalHorasExtrasFormatado
FROM 
    JornadasTrabalho
WHERE
    startJornada BETWEEN '2023-11-26' AND '2023-12-25'
    AND (porcentagem = 70 OR porcentagem = 110)
GROUP BY porcentagem;


SELECT * FROM jornadastrabalho
WHERE startJornada BETWEEN '2023-11-26' AND '2023-12-25';
