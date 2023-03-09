<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Facture;
use App\Repository\FactureRepository;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Component\HttpFoundation\Request;
use App\Form\FactureType;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Dompdf\Dompdf;
use Dompdf\Options;
use Twilio\Rest\Client;
use Knp\Component\Pager\PaginatorInterface;
class FactureController extends AbstractController
{
    #[Route('/facture', name: 'app_facture')]
    public function index(): Response
    {
        return $this->render('facture/index.html.twig', [
            'controller_name' => 'FactureController',
        ]);
    }
    #[Route('/addfacture', name: 'addfacture')]
    public function addfacture(ManagerRegistry $doctrine,
    Request $request):Response{
    $facture= new facture();
    $form=$this->createForm(factureType::class,
    $facture);
    $form->handleRequest($request);
    
    //Action d'ajout
    if($form->isSubmitted() && $form->isValid()){
    $em =$doctrine->getManager() ;
    $em->persist($facture);
    $em->flush();
    return $this->redirectToRoute("afficheF");}
    return $this->renderForm("facture/ajoutfacture.html.twig",
    array("add"=>$form));
    }
    
    #[Route('/modiffacture/{id}', name: 'modiffacture')]
    public function modiffacture(ManagerRegistry $doctrine,
    Request $request, $id ,FactureRepository $r){
    $facture= $r->find($id);
    $form=$this->createForm(FactureType::class,
    $facture);
    $form->handleRequest($request);
    //Action update
    if($form->isSubmitted()){
    $em =$doctrine->getManager() ;
    $em->flush();
    return $this->redirectToRoute("afficheF");}
    return $this->renderForm("facture/ajoutfacture.html.twig",
    array("add"=>$form));
    }
    
    #[Route('/afficheF', name: 'afficheF')]
    public function afficheC(): Response
    {
        $r=$this->getDoctrine()->getRepository(facture::class);
        $c=$r->findall();


        return $this->render('facture/affichage.html.twig', [
            'factures' => $c,
        ]);
    }
    #[Route('/suppfacture/{id}', name: 'suppfacture')]
    public function suppC(FactureRepository $r, $id, ManagerRegistry $M): Response
    {
    
        $facture=$r->find($id);
        //recup entity manager
        $em=$M->getManager();
        //action de suppression

        $em->remove($facture);
        $em->flush();



        return $this->redirectToRoute('afficheF');
    }
    #[Route('/rechercheF', name: 'rechercheF')]
    public function rechercheF(Request $request): Response
    {
        $entityManager = $this->getDoctrine()->getManager();
        $factures = $entityManager->getRepository(Facture::class)->findAll();
    
        if ($request->isMethod('POST')) {
            $nom = $request->request->get('nom_client');
            $factures = $entityManager->getRepository(Facture::class)->findBy(['nom_client' => $nom]);
        }
    
        return $this->render('facture/recherche.html.twig', [
            'facture' => $factures,
        ]);
    }
    #[Route('/pdf/{id}', name: 'pdf', methods: ['GET'])]
    public function constatp(Facture $facture): Response
    {
        // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');
        
        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);
        
        // Retrieve the HTML generated in our twig file
        $html = $this->renderView('facture/pdffacture.html.twig', [
            'facture' => $facture,
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

    #[Route('/afficheF', name: 'afficheF')]
    public function affichefacture(FactureRepository $r,Request $request, PaginatorInterface $Paginator): Response
    {
    $v=$r->findall();
    $pagination = $Paginator->paginate(
        $r->paginationQuery(),
        $request->query->get('page',1),
        2




    );

    return $this->render('facture/affichage.html.twig', [
        'pagination' => $pagination,
    ]);
    }
    

    
}
