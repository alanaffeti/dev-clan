<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use CMEN\GoogleChartsBundle\GoogleCharts\Charts\Material\BarChart;

class StatsController extends AbstractController
{
    #[Route('/stats', name: 'app_stats')]
    public function index(): Response
    {
        return $this->render('stats/index.html.twig', [
            'controller_name' => 'StatsController',
        ]);
    }
    #[Route("/stats", name:"rec_stat")]
    
    public function statistiques (ConstatsRepository $rep){

        //chercher les types de constats

        $constats = $rep->countByNbr();

        $constDate = [];
        $ConstCount = [];


        foreach($constats as $constat){

            // $constType[] = $constat->getType();
            $constType[] = $constat ['Date'];
            $constCount[]= $constat ['nbr'];
            // $constCount[] = count($recType);
        }
        return $this->render('  /stats.html.twig', [
            'constDate' => json_encode($constDate),
            'ConstCount' => json_encode($constCount),


        ]);

        
    }
}
