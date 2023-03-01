<?php









namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Voiture;
use App\Entity\Contrat;
use Symfony\Component\HttpFoundation\Request;

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



}
