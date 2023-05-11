<?php

namespace App\Repository;

use App\Entity\Reclamation;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;
use Doctrine\ORM\Query\ResultSetMappingBuilder;
use Doctrine\ORM\Query\Expr\Func;



/**
 * @extends ServiceEntityRepository<Reclamation>
 *
 * @method Reclamation|null find($id, $lockMode = null, $lockVersion = null)
 * @method Reclamation|null findOneBy(array $criteria, array $orderBy = null)
 * @method Reclamation[]    findAll()
 * @method Reclamation[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class ReclamationRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Reclamation::class);
    }

    public function save(Reclamation $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function remove(Reclamation $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }
  



   /* public function sewy($date_rec){
        return $this->createQueryBuilder('reclamation')
        ->select('count(reclamation.id)')
        ->where('reclamation.date_rec = :reclamation')
        // ->where('r.typee LIKE : reclamation')
        ->setParameter('reclamation', $date_rec)
        ->getQuery()
        ->getSingleScalarResult();
    }*/
   /* public function countByDate(){
        //$query = $this->createQueryBuilder('a')
        //     ->select('SUBSTRING(a.created_at, 1, 10) as dateAnnonces, COUNT(a) as count')
        //     ->groupBy('dateAnnonces')
        // ;
        // return $query->getQuery()->getResult();
        $query = $this->getEntityManager()->createQuery("
            SELECT SUBSTRING(a.date_rec, 1, 10) as dateRec, COUNT(a) as count FROM App\Entity\Reclamation a GROUP BY date_rec
        ");
        return $query->getResult();
    }*/

//    /**
//     * @return Reclamation[] Returns an array of Reclamation objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('r')
//            ->andWhere('r.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('r.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?Reclamation
//    {
//        return $this->createQueryBuilder('r')
//            ->andWhere('r.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
public function barDep(){
    return $this->createQueryBuilder('reclamation')
    ->select('count(reclamation.id)')
    ->where('reclamation.type_rec LIKE :reclamation')
    // ->where('r.typee LIKE : reclamation')
    ->setParameter('reclamation','1')
    ->getQuery()
    ->getSingleScalarResult();
}

public function barArr(){
    return $this->createQueryBuilder('reclamation')
    ->select('count(reclamation.id)')
     ->where('reclamation.type_rec LIKE :reclamation')
    // ->where('r.typee LIKE :  reclamation')
    ->setParameter('reclamation','2')
    ->getQuery()
    ->getSingleScalarResult();
}
public function findBySearchQuery(string $query)
    {
        $rsm = new ResultSetMappingBuilder($this->_em);
        $rsm->addRootEntityFromClassMetadata(Reclamation::class, 'a');

        $sql = 'SELECT a.* FROM reclamation a WHERE a.Nom LIKE :query ';
        $nativeQuery = $this->_em->createNativeQuery($sql, $rsm);
        $nativeQuery->setParameter('query', '%'.$query.'%');

        return $nativeQuery->getResult();
    }

}
