<?php
namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Voiture;
use App\Entity\Contrat;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Dompdf\Dompdf;
use Dompdf\Options;
use CMEN\GoogleChartsBundle\GoogleCharts\Charts\Material\BarChart;
use DateTime;


use App\Repository\VoitureRepository;
use App\Repository\ContratRepository;
use Doctrine\Persistence\ManagerRegistry;



class ContratController extends AbstractController
{
    #[Route('/contrat', name: 'app_contrat')]
    public function index(): Response
    {
        return $this->render('contrat/index.html.twig', [
            'controller_name' => 'ContratController',
        ]);
    }

    public function delete(Request $request, Contrat $contrat)
    {
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->remove($contrat);
        $entityManager->flush();
    
        $this->addFlash('success', 'Contrat deleted successfully');
    
        return $this->redirectToRoute('viewContrats', ['id' => $contrat->getVoiture()->getId()]);
    }
#[Route('/modifV/{id}', name: 'modifV')]
public function modifclassroom(ManagerRegistry $doctrine, Request $request, $id, VoitureRepository $r) {
    $voiture = $r->find($id);
    $contrats = $voiture->getContrats();
    foreach ($contrats as $contrat) {
        $voiture->removeContrat($contrat);
    }
    $form = $this->createForm(VoitureType::class, $voiture);
    foreach ($contrats as $contrat) {
        $form->get('contrats')->add(''.$contrat->getId(), ContratType::class, [
            'data' => $contrat,
            'label' => false,
        ]);
    }
    $form->handleRequest($request);
    //Action update
    if($form->isSubmitted() && $form->isValid()){
        foreach ($contrats as $contrat) {
            $contrat->setVoiture(null);
            $doctrine->getManager()->remove($contrat);
        }
        $voiture = $form->getData();
        $em = $doctrine->getManager();
        foreach ($voiture->getContrats() as $contrat) {
            $contrat->setVoiture($voiture);
            $em->persist($contrat);
        }
        $em->persist($voiture);
        $em->flush();
        return $this->redirectToRoute('modifV', ['id' => $voiture->getId()]);
    }
    return $this->render('contrat/add.html.twig', [
        'voiture' => $voiture,
        'f' => $form->createView(),
    ]);
}


#[Route('/pdf/{id}', name: 'pdf', methods: ['GET'])]
    public function constatp(Contrat $contrat): Response
    {

        $date = new DateTime();
        echo $date->format('Y-m-d H:i:s');

        // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');
        
        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);
        
        // Retrieve the HTML generated in our twig file
        $html = $this->renderView('contrat/contratpdf.html.twig', [
            'contrat' => $contrat,
            'current_date' => $date,

        ]);
        
        // Load HTML to Dompdf
        $dompdf->loadHtml($html);
        
        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (inline view)
        $pdfOutput = $dompdf->output();
        return new Response($pdfOutput, 200, [
            'Content-Type' => 'application/pdf',
        ]);
    }

    #[Route('/statisccontrat', name: 'stat', methods: ['GET'])]
public function statisreclamation(ContratRepository $r)
{
    //on va chercher les categories
    $rech = $r->barDep();
    $arr = $r->barArr();
    
    $bar = new barChart ();
    $bar->getData()->setArrayToDataTable(
        [['contrat', 'etat'],
         ['en cours', intVal($rech)],
         ['traite', intVal($arr)],
        ]
    );

    $bar->getOptions()->setTitle('les contrat');
    $bar->getOptions()->getHAxis()->setTitle('Nombre de contrat');
    $bar->getOptions()->getHAxis()->setMinValue(0);
    $bar->getOptions()->getVAxis()->setTitle('etat');
    $bar->getOptions()->SetWidth(800);
    $bar->getOptions()->SetHeight(400);


    return $this->render('voiture/statics.html.twig', array('bar'=> $bar )); 

}

}
