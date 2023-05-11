<?php

namespace App\Repository;

use App\Entity\Constat;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Constat>
 *
 * @method Constat|null find($id, $lockMode = null, $lockVersion = null)
 * @method Constat|null findOneBy(array $criteria, array $orderBy = null)
 * @method Constat[]    findAll()
 * @method Constat[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class ConstatRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Constat::class);
    }

    public function save(Constat $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function remove(Constat $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

   
// public function getStatisticsByDate()
// {
//     return $this->createQueryBuilder('constat')
//         ->select('DATE(constat.date) as date, count(constat.id) as count')
//         ->groupBy('date')
//         ->getQuery()
//         ->getResult();
// }
// public function getMonthlyCarCounts()
// {
//     $qb = $this->createQueryBuilder('r')
//         ->select('MONTH(r.date) AS month, YEAR(r.date) AS year, COUNT(r.id) AS constatCount')
//         ->groupBy('year, month')
//         ->orderBy('year, month');

//     return $qb->getQuery()->getResult();
// }

// public function getMonthlyConstatCounts()
// {
//     $qb = $this->createQueryBuilder('c')
//         ->select('MONTH(c.date) AS month, YEAR(c.date) AS year, COUNT(c.id) AS constatCount')
//         ->groupBy('year, month')
//         ->orderBy('year, month');

//     return $qb->getQuery()->getResult();
// }

public function barDep(){
    return $this->createQueryBuilder('r')
    ->select('count(r.id)')
    ->where('r.lieu LIKE :constat')
    // ->where('r.typee LIKE : reclamation')
    ->setParameter('constat','tunis')
    ->getQuery()
    ->getSingleScalarResult();
}

public function barArr(){
    return $this->createQueryBuilder('r')
    ->select('count(r.id)')
     ->where('r.lieu LIKE :constat')
    // ->where('r.typee LIKE :  reclamation')
    ->setParameter('constat','gabes')
    ->getQuery()
    ->getSingleScalarResult();
}




}
