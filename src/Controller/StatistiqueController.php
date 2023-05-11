<?php

namespace App\Controller;

use App\Entity\Reclamation;
use App\Repository\ReclamationRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use CMEN\GoogleChartsBundle\GoogleCharts\Charts\Material\BarChart;

class StatisticController extends AbstractController
{
    /**
     * @Route("/statistic", name="app_statistic")
     */
    public function index(): Response
    {
        return $this->render('statistic/stats.html.twig', [
            'controller_name' => 'StatisticController',
        ]);
    }


   /**
    * @Route("/stats", name="rec_stat")
    */
    public function statistiques (ContratRepository $rep){

        //chercher les types de reclamation

        $contrats = $rep->countByNbr();

        $recetat = [];
        $recCount = [];


        foreach($contrats as $contrat){

            // $recType[] = $reclamation->getType();
            $recetat[] = $contrat ['nom'];
            $recCount[]= $contrat ['nbr'];
            // $recCount[] = count($recType);
        }
        return $this->render('statistic/stats.html.twig', [
            'recType' => json_encode($recetat),
            'recCount' => json_encode($recCount),


        ]);

        
    }
}