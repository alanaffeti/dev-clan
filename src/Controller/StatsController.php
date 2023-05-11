<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class StatsController extends AbstractController
{
    #[Route('/stats', name: 'app_stats')]
    public function index(): Response
    {
        return $this->render('stats/index.html.twig', [
            'controller_name' => 'StatsController',
        ]);
    }
    /**
    * @Route("/stats", name="rec_stat")
    */
    public function statistiques (ReclamationRepository $rep){

        //chercher les types de reclamation

        $reclamations = $rep->countByNbr();

        $recType = [];
        $recCount = [];


        foreach($reclamations as $reclamation){

            // $recType[] = $reclamation->getType();
            $recType[] = $reclamation ['Type'];
            $recCount[]= $reclamation ['nbr'];
            // $recCount[] = count($recType);
        }
        return $this->render('  /stats.html.twig', [
            'recType' => json_encode($recType),
            'recCount' => json_encode($recCount),


        ]);

        
    }
    
}

